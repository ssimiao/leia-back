package br.com.book;

import br.com.shared.google.*;
import br.com.shared.exception.ResourceNotFoundException;
import br.com.user.UserEntity;
import br.com.user.UserRepository;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/v1/books")
public class BookResource {

    @Inject
    private UserRepository userRepository;

    @RestClient
    private GoogleBooksClient client;

    @RestClient
    private GoogleTranslate googleTranslate;

    @Inject
    private BookRepository bookRepository;

    @POST
    @Transactional
    public Response recommendBook(BookRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId());
        if (userEntity.getUserType().equalsIgnoreCase("professor")) {
            BookEntity findableBook = bookRepository.findByIsbn(request.getIsbn());

            if (findableBook != null) {
                findableBook.setNumberOfRecommendation(findableBook.getNumberOfRecommendation() + 1);
                findableBook.setGroupOnly(false);
                bookRepository.persist(findableBook);
            } else {
                findableBook = insertNewBook(request);
            }

            return Response.ok(Map.of("id_book", findableBook.getId())).build();
        }
        return Response.status(422).build();
    }

    private BookEntity insertNewBook(BookRequest request) {
        GoogleBookData googleBookData = client.buscaLivroPorISBN("isbn:".concat(request.getIsbn()));
        List<GoogleBookItem> items = googleBookData.items;
        if (Objects.isNull(items))
            throw new ResourceNotFoundException("Livro não encontrado");

        GoogleBookItem book = items.stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Livro Não encontrado"));
        GoogleBookVolume volumeInfo = book.getVolumeInfo();

        String category = volumeInfo.getCategories().stream().findFirst().orElse("Não identificada");

        try {
            if(!category.equalsIgnoreCase("Não identificada"))
                category = googleTranslate.translate(new GoogleTranslateRequest(category)).getData().getTranslations().get(0).getTranslateText();
        } catch (Exception e) {
            Log.info("Não foi possível traduzir o texto: ".concat(category));
        }

        BookEntity bookEntity = new BookEntity()
                .setIsbn(volumeInfo.getIndustryIdentifiers().get(1).getIdentifier())
                .setIsbn13(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier())
                .setName(volumeInfo.getTitle())
                .setPages(volumeInfo.getPageCount())
                .setCategory(category)
                .setNumberOfRecommendation(1)
                .setGroupOnly(false);

        bookRepository.persist(bookEntity);
        return bookEntity;
    }

    @GET
    public Response getBook(@QueryParam("isbn") String isbn, @QueryParam("q") String query) {
        if (isbn == null && query == null)
            return Response.ok(bookRepository.listAll().stream().filter(b -> !b.getIsbn().contains("isbn") && !b.getIsbn13().contains("isbn") && !b.getName().equalsIgnoreCase("isbn")).toList()).build();
        if (query == null)
            return Response.ok(bookRepository.findByIsbn(isbn)).build();

        return Response.ok(bookRepository.findByQueryValue(query)).build();
    }
}
