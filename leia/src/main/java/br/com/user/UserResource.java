package br.com.user;

import br.com.character.CharacterData;
import br.com.security.Token;
import br.com.security.TokenClient;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/v1/users")
public class UserResource {

    @Inject
    private UserRepository userRepository;

    @RestClient
    private AccountRestClient accountRestClient;

    @RestClient
    private TokenClient tokenClient;

    @POST
    public Response saveUser(Request<UserCharacterData> userRequest) {
        UserCharacterData userCharacterData = userRequest.getRequest();
        CharacterData character = userCharacterData.getCharacter();
        if ("professor".equalsIgnoreCase(userCharacterData.getAccount().getUserType()))
            character.setClasseId("professor");
        else
            character.setClasseId("novato");

        Token token = tokenClient.generateToken();
        Log.info(token.getAccessToken());
        Response response = accountRestClient.saveUserAndChar(userRequest, "Bearer " + token.getAccessToken());
        return Response.status(response.getStatus()).build();
    }
}
