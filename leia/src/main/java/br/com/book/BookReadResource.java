package br.com.book;

import br.com.shared.exception.ReadException;
import br.com.shared.exception.ResourceNotFoundException;
import br.com.teacher.GroupStudentEntity;
import br.com.teacher.GroupStudentRepository;
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

    @Inject
    private GroupStudentRepository groupStudentRepository;

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

        if (book.getGroupOnly() && !filterGroupUserHasBookRecommended(userId, bookRequest))
            throw new ReadException("Você não pode adicionar esse livro");

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

    private boolean filterGroupUserHasBookRecommended(Long userId, BookRequest bookRequest) {
        return groupStudentRepository.streamAll()
                .anyMatch(group -> group.getStudent().stream().anyMatch(student -> student.getId().equals(userId) &&
                        group.getBooksRecommended().stream()
                                .anyMatch(bookRecommended -> bookRecommended.getIsbn13().equalsIgnoreCase(bookRequest.getIsbn()) || bookRecommended.getIsbn().equalsIgnoreCase(bookRequest.getIsbn()))
                ));
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
        List<BookReadEntity> reads = bookReadRepository.findByUserId(userId).stream().peek(read -> {
            UserEntity user = read.getUser();
            user.setPassword("******");
            user.setUsername("******");
        }).toList();
        return Response.ok(reads).build();
    }
}
