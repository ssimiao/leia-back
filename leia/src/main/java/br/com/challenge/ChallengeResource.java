package br.com.challenge;

import br.com.book.BookEntity;
import br.com.book.BookRepository;
import br.com.shared.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.ArrayList;

@Path("/v1/books/{idBook}/challenges")
public class ChallengeResource {

    @Inject
    private BookRepository bookRepository;

    @Inject
    private ChallengeRepository challengeRepository;

    @POST
    @Transactional
    public Response addChallenge(@RestPath Long idBook, ChallengeRequest challengeRequest) throws JsonProcessingException {
        BookEntity book = bookRepository.findById(idBook);

        if (book == null)
            throw new ResourceNotFoundException("Livro não encontrado");

        if (challengeRequest.isAuto()) {
            return Response.status(501).build();
        }

        ObjectMapper mapper = new ObjectMapper();

        String challenge = switch (challengeRequest.getChallengeType()) {
            case "puzzle" -> mapper.writeValueAsString(challengeRequest.getPuzzle());
            case "search" -> mapper.writeValueAsString(challengeRequest.getSearch());
            case "quiz" -> mapper.writeValueAsString(challengeRequest.getQuiz());
            default -> throw new RuntimeException("Erro ao escolher um desafio");
        };

        ChallengeEntity challengeEntity = new ChallengeEntity()
                .setChallengeType(challengeRequest.getChallengeType())
                .setChallenge(challenge);

        challengeRepository.persist(challengeEntity);

        ArrayList<ChallengeEntity> challenges = new ArrayList<>();
        challenges.add(challengeEntity);
        book.setChallenge(challenges);
        bookRepository.persist(book);

        return Response.ok().build();
    }
}
