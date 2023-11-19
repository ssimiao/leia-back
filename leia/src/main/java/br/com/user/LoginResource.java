package br.com.user;

import br.com.security.Token;
import br.com.security.TokenClient;
import br.com.shared.exception.UserAndCharacterNotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@Path("/v1/login")
public class LoginResource {

    @RestClient
    private AccountRestClient accountRestClient;

    @Inject
    private UserRepository userRepository;

    @RestClient
    private TokenClient tokenClient;

    @POST
    public Response login(UserData user, @QueryParam("process") String process) {
        if (process.equalsIgnoreCase("java")) {
            UserEntity userEntity = userRepository.find("username", user.getUsername()).firstResult();

            if (userEntity != null && user.getPassword().equalsIgnoreCase(userEntity.getPassword())) {
                return Response.ok().build();
            }

            return Response.status(403).build();
        }

        return salesforceLogin(user);
    }

    private Response salesforceLogin(UserData user) {
        Token token = tokenClient.generateToken();
        accountRestClient.get(user.getUsername(),  "Bearer ".concat(token.getAccessToken()));

        UserData userResponse = accountRestClient.getUser(user.getUsername(), "Bearer ".concat(token.getAccessToken()))
                .stream().findFirst()
                .orElseThrow(() -> new UserAndCharacterNotFoundException("Usuário não encontrado"))
                .getAccount();

        if (userResponse.getPassword().equalsIgnoreCase(user.getPassword())) {
            return Response.ok(Map.of("token", token.getAccessToken())).build();
        }

        return Response.status(403).build();
    }
}
