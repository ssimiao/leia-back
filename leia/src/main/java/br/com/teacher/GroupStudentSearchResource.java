package br.com.teacher;

import br.com.book.BookReadEntity;
import br.com.book.BookReadRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.HashMap;
import java.util.Map;

@Path("/v1/users/{userId}/groups/{groupId}")
public class GroupStudentSearchResource {

    @Inject
    private GroupStudentRepository groupStudentRepository;

    @Inject
    private BookReadRepository bookReadRepository;

    @GET
    public Response getGroupById(@RestPath Long userId, @RestPath Long groupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", groupId);
        params.put("owner", userId);

        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or id = :id", params).firstResult();
        groupStudentEntity.getBooksRecommended().forEach(it -> {
            it.setNumberOfReaders(groupStudentEntity.getStudent().size());
            bookReadRepository.find("book.id", it.getId()).stream().forEach(read -> {
                if (read.getChallengeAnswered() && groupStudentEntity.getStudent().stream().anyMatch(student -> student.getUser().getId().equals(read.getUser().getId()))) {
                    read.getBook().setFinishReaders(read.getBook().getFinishReaders() + 1);
                }
                read.getBook().setNumberOfReaders(groupStudentEntity.getStudent().size());
            });
        });
        return Response.ok(groupStudentEntity).build();
    }
}
