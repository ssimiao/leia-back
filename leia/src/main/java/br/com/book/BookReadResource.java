package br.com.book;

import br.com.shared.exception.ReadException;
import br.com.shared.exception.ResourceNotFoundException;
import br.com.user.UserEntity;
import br.com.user.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;
import java.util.Map;

@Path("/v1/users/{userId}/reads")
public class BookReadResource {

    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookReadRepository bookReadRepository;

    @Inject
    private UserRepository userRepository;

    @POST
    @Transactional
    public Response addBookToRead(@RestPath Long userId, BookRequest bookRequest) {
        List<BookReadEntity> reads = bookReadRepository.findByUserIdAndIsbn(userId, bookRequest.getIsbn());

        if (!reads.isEmpty())
            throw new ReadException("Livro já está na sua mochila de missões");

        UserEntity userEntity = userRepository.findById(userId);
        if (userEntity == null)
            throw new ResourceNotFoundException("Usuário não encontrado");

        BookEntity book = bookRepository.findByIsbn(bookRequest.getIsbn());
        if (book == null)
            throw new ResourceNotFoundException("Livro não encontrado");

        if (book.getChallenge().isEmpty())
            throw new ReadException("Sem desafios adicionados ao livro.");



        BookReadEntity bookReadEntity = new BookReadEntity()
                .setBook(book)
                .setPagesRead(0)
                .setChapter(1)
                .setChallengeAnswered(false)
                .setUser(userEntity);

        bookReadRepository.persist(bookReadEntity);

        return Response
                .ok(Map.of("id", bookReadEntity.getId()))
                .build();
    }

    @PATCH
    @Transactional
    public Response updateBookRead(@RestPath Long userId, BookRequest bookRequest) {
        UserEntity userEntity = userRepository.findById(userId);
        if (userEntity == null)
            throw new ResourceNotFoundException("Usuário não encontrado");

        BookReadEntity read = bookReadRepository.findById(bookRequest.getReadId());
        if (read == null)
            throw new ResourceNotFoundException("Leitura não encontrada");

        if (bookRequest.getPagesRead() < read.getPagesRead() || bookRequest.getChapter() < read.getChapter())
            throw new ReadException("Atualização da leitura não pode ser processada");

        read.setChapter(bookRequest.getChapter())
                .setPagesRead(bookRequest.getPagesRead());

        bookReadRepository.persist(read);

        return Response.ok().build();
    }

    @GET
    public Response getBookToRead(@RestPath Long userId) {
        return Response.ok(bookReadRepository.findByUserId(userId)).build();
    }
}
