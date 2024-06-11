package br.com.character.action;


import br.com.character.CharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.Map;

@Path("/v1/sync")
public class CharacterSyncResource {


    @Inject
    private CharacterRepository characterRepository;

    @POST
    public Response actionResolve(Map<Object, Object> request) {
        try {
            Log.info(new ObjectMapper().writeValueAsString(request));
        } catch (Exception e) {
            Log.info("erro sync: ".concat(e.getMessage()));
        }
        return Response.ok().build();
    }
}
