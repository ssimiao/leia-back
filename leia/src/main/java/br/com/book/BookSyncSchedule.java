package br.com.book;

import br.com.challenge.ChallengeEntity;
import br.com.challenge.ChallengeRepository;
import br.com.challenge.PuzzleRequest;
import br.com.challenge.SearchWordRequest;
import br.com.security.Token;
import br.com.security.TokenClient;
import br.com.shared.salesforce.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ApplicationScoped
public class BookSyncSchedule {

    @RestClient
    private BookSalesforce bookSalesforce;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private ChallengeRepository challengeRepository;

    @RestClient
    private TokenClient tokenClient;

    @RestClient
    private GameSalesforce gameSalesforce;

    @Scheduled(every = "60s", delay = 30, delayUnit = TimeUnit.SECONDS)
    @Transactional
    public void syncBooks() {
        Token token = tokenClient.generateToken();
        var books = bookRepository.findAll().stream().toList();
        ProductWrapper booksInSalesforce = bookSalesforce.getBookSalesforce("Book", "Bearer ".concat(token.getAccessToken()));

        List<BookEntity> bookList = booksInSalesforce.getProducts().stream()
                .filter(product -> nonNull(product.getIsbn10()) && nonNull(product.getIsbn13()))
                .filter(Product::getActive)
                .filter(it ->
                        books.stream().noneMatch(bookDatabase -> bookDatabase.getIsbn13().equalsIgnoreCase(it.getIsbn13()) ||
                                bookDatabase.getIsbn().equalsIgnoreCase(it.getIsbn10())))
                .map(it -> {
                    BookEntity bookEntity = new BookEntity()
                            .setCategory("Não identificada")
                            .setIsbn(it.getIsbn10())
                            .setIsbn13(it.getIsbn13())
                            .setName(it.getName())
                            .setNumberOfRecommendation(1)
                            .setPages(it.getPageNumber())
                            .setGroupOnly(false);

                    ObjectMapper objectMapper = new ObjectMapper();
                    var challengeBook = gameSalesforce.getGamesFromIsbn(it.getIsbn10(), "Bearer " + token.getAccessToken());
                    if (challengeBook.getGame() != null) {
                        List<ChallengeEntity> challenges = challengeBook.getGame().stream().map(challenge -> {
                                    ChallengeEntity challengeEntity = new ChallengeEntity();
                                    try {
                                        if (challenge.getType().equalsIgnoreCase("Memory")) {
                                            MemoryWrapper memoryWrapper = objectMapper.readValue(challenge.getPayload(), MemoryWrapper.class);
                                            String challengeParse = memoryWrapper.getGames().stream().map(MemoryGame::getUrl).collect(Collectors.joining("|"));
                                            challengeEntity.setChallengeType("memory");
                                            challengeEntity.setChallenge(challengeParse);
                                        } else if (challenge.getType().equalsIgnoreCase("WordSearch")) {
                                            WordWrapper wordWrapper = objectMapper.readValue(challenge.getPayload(), WordWrapper.class);
                                            List<String> listaDePalavras = wordWrapper.getGames().stream().map(word -> word.getWord().toUpperCase()).toList();

                                            SearchWordRequest searchWordRequest = new SearchWordRequest();
                                            searchWordRequest.setWords(listaDePalavras);
                                            searchWordRequest.setHint("Sem dica");
                                            String challengeParse = objectMapper.writeValueAsString(searchWordRequest);
                                            challengeEntity.setChallengeType("search");
                                            challengeEntity.setChallenge(challengeParse);
                                        }
                                    } catch (JsonProcessingException e) {
                                        Log.warn("não processou/sincronizou o livro: " + it.getName());
                                        Log.warn(e.getMessage());
                                    }
                                    return challengeEntity;
                                }).filter(challengeEntity -> challengeEntity.getChallengeType() != null)
                                .toList();
                        challengeRepository.persist(challenges);
                        bookEntity.setChallenge(challenges);
                    }


                    return bookEntity;
                }).filter(it -> it.getChallenge() != null && !it.getChallenge().isEmpty())
                .toList();

        bookRepository.persist(bookList);
    }
}
