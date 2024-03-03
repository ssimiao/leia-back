package br.com.teacher;

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

    @GET
    public Response getGroupById(@RestPath Long userId, @RestPath Long groupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", groupId);
        params.put("owner", userId);
        GroupStudentEntity groupStudentEntity = groupStudentRepository.find("owner = :owner or id = :id", params).firstResult();
        return Response.ok(groupStudentEntity).build();
    }
}
