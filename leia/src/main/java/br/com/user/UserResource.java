package br.com.user;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/v1/users")
public class UserResource {

    @Inject
    private UserRepository userRepository;

    @POST
    @Transactional
    public Response saveUser(UserRequest userRequest) {
        userRepository.persist(new UserEntity(userRequest.getUsername(), userRequest.getPassword()));

        return Response.ok(userRequest).build();
    }
}
