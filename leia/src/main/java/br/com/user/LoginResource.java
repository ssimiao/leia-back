package br.com.user;

import br.com.security.Token;
import br.com.security.TokenClient;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@Path("/v1/login")
public class LoginResource {

    @RestClient
    private AccountRestClient accountRestClient;

    @RestClient
    private TokenClient tokenClient;

    @POST
    public Response login(UserData user) {
        Token token = tokenClient.generateToken();
        UserData userResponse = accountRestClient.getUser(user.getUsername(), "Bearer ".concat(token.getAccessToken())).getAccount();

        if (userResponse.getPassword().equalsIgnoreCase(user.getPassword())) {
            return Response.ok(Map.of("token", token.getAccessToken())).build();
        }

        return Response.status(403).build();
    }
}
