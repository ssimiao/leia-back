package br.com.book;

import br.com.security.Token;
import br.com.security.TokenClient;
import br.com.shared.salesforce.BookSalesforce;
import br.com.shared.salesforce.Product;
import br.com.shared.salesforce.ProductWrapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class BookSyncSchedule {

    @RestClient
    private BookSalesforce bookSalesforce;

    @Inject
    private BookRepository bookRepository;

    @RestClient
    private TokenClient tokenClient;

    @Scheduled(every = "30s")
    @Transactional
    public void syncBooks() {
        Token token = tokenClient.generateToken();
        var books = bookRepository.findAll().stream().toList();
        ProductWrapper booksInSalesforce = bookSalesforce.getBookSalesforce("Book", "Bearer ".concat(token.getAccessToken()));

        List<BookEntity> bookList = booksInSalesforce.getProducts().stream()
                .filter(it ->
                        books.stream().noneMatch(bookDatabase -> bookDatabase.getIsbn13().equalsIgnoreCase(it.getIsbn13()) ||
                                bookDatabase.getIsbn().equalsIgnoreCase(it.getIsbn10())))
                .filter(Product::getActive)
                .map(it -> new BookEntity()
                        .setCategory("Não identificada")
                        .setIsbn(it.getIsbn10())
                        .setIsbn13(it.getIsbn13())
                        .setName(it.getName())
                        .setNumberOfRecommendation(1)
                        .setPages(it.getPageNumber())
                        .setGroupOnly(false)).toList();

        bookRepository.persist(bookList);
    }
}
