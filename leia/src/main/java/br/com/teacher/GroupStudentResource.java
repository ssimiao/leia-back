package br.com.teacher;

import br.com.character.CharacterEntity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/v1/user/{userId}/groups")

public class GroupStudentResource {

    @Inject
    private GroupStudentRepository groupStudentRepository;

    @POST
    @Transactional
    public Response createGroup(@RestPath Long userId, GroupStudentRequest request) {
        GroupStudentEntity groupStudentEntity = new GroupStudentEntity(userId, request.getName(), List.of());
        groupStudentRepository.persist(groupStudentEntity);

        return Response.ok(groupStudentEntity).build();
    }

    @PATCH
    @Transactional
    public Response addCharToGroup(@RestPath Long userId, GroupStudentRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("owner", userId);
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or name = :name", params).firstResult();
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
        return Response.ok(groupStudentEntity).build();
    }

    @GET
    public Response getGroup(@RestPath Long userId) {
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner", userId).firstResult();
        return Response.ok(groupStudentEntity).build();
    }
}
