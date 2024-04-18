package br.com.teacher;

import br.com.book.BookEntity;
import br.com.book.BookRepository;
import br.com.book.BookRequest;
import br.com.character.CharacterEntity;
import br.com.shared.exception.ResourceNotFoundException;
import br.com.shared.google.GoogleBookData;
import br.com.shared.google.GoogleBookItem;
import br.com.shared.google.GoogleBookVolume;
import br.com.shared.google.GoogleBooksClient;
import br.com.user.UserEntity;
import br.com.user.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/v1/users/{userId}/groups")

public class GroupStudentResource {

    @Inject
    private GroupStudentRepository groupStudentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private BookRepository bookRepository;

    @RestClient
    private GoogleBooksClient client;

    @POST
    @Transactional
    public Response createGroup(@RestPath Long userId, GroupStudentRequest request) {
        GroupStudentEntity groupStudentEntity = new GroupStudentEntity(userId, request.getName(), List.of(), List.of());
        groupStudentRepository.persist(groupStudentEntity);

        return Response.ok(groupStudentEntity).build();
    }

    @PATCH
    @Transactional
    public Response addCharToGroup(@RestPath Long userId, GroupStudentRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("owner", userId);
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or groupName = :name", params).firstResult();
        List<CharacterEntity> student = groupStudentEntity.getStudent();
        List<CharacterEntity> characters = request.getCharacters().stream().map(id -> {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setId(id);
            return characterEntity;
        }).toList();
        if (Objects.nonNull(student)) {
            student.addAll(characters);
        } else {
            groupStudentEntity.setStudent(characters);
        }

        groupStudentRepository.persist(groupStudentEntity);
        return Response.ok().build();

    }

    @PUT
    @Transactional
    public Response addBookToGroup(@RestPath Long userId, GroupStudentRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("owner", userId);
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or groupName = :name", params).firstResult();

        UserEntity userEntity = userRepository.findById(userId);
        if (userEntity.getUserType().equalsIgnoreCase("professor")) {
            BookEntity findableBook = bookRepository.findByIsbn(request.getIsbn());

            if (findableBook != null) {
                findableBook.setNumberOfRecommendation(findableBook.getNumberOfRecommendation() + 1);
                bookRepository.persist(findableBook);
            } else {
                findableBook = insertNewBook(request);
            }

            groupStudentEntity.getBooksRecommended().add(findableBook);
            groupStudentRepository.persist(groupStudentEntity);
            return Response.ok(Map.of("id_book", findableBook.getId())).build();
        }

        return Response.serverError().build();
    }

    @DELETE
    @Transactional
    public Response removeGroupOrStudent(@RestPath Long userId, GroupStudentRequest request) {
        boolean isRemovableGroupAction = request.getCharacters() == null || request.getCharacters().isEmpty();
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("owner", userId);
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or groupName = :name", params).firstResult();

        if (groupStudentEntity == null)
            throw new ResourceNotFoundException("Grupo não encontrado");

        if (isRemovableGroupAction) {
            groupStudentRepository.deleteById(groupStudentEntity.getId());
        } else {
            List<CharacterEntity> lista = groupStudentEntity.getStudent().stream()
                    .filter(it -> !Objects.equals(it.getId(), request.getCharacters().get(0))).toList();

            groupStudentEntity.setStudent(lista);
            groupStudentRepository.persist(groupStudentEntity);
        }

        return Response.ok().build();
    }

    private BookEntity insertNewBook(GroupStudentRequest request) {
        GoogleBookData googleBookData = client.buscaLivroPorISBN("isbn:".concat(request.getIsbn()));
        List<GoogleBookItem> items = googleBookData.items;
        if (Objects.isNull(items))
            throw new ResourceNotFoundException("Livro não encontrado");

        GoogleBookItem book = items.stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Livro Não encontrado"));
        GoogleBookVolume volumeInfo = book.getVolumeInfo();

        BookEntity bookEntity = new BookEntity()
                .setIsbn(volumeInfo.getIndustryIdentifiers().get(1).getIdentifier())
                .setIsbn13(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier())
                .setName(volumeInfo.getTitle())
                .setPages(volumeInfo.getPageCount())
                .setCategory(volumeInfo.getCategories().stream().findFirst().orElse(null))
                .setNumberOfRecommendation(1)
                .setGroupOnly(true);

        bookRepository.persist(bookEntity);
        return bookEntity;
    }

    @GET
    public Response getGroup(@RestPath Long userId) {
        var groupStudentEntity = groupStudentRepository.find("owner", userId).stream().toList();
        return Response.ok(groupStudentEntity).build();
    }
}
