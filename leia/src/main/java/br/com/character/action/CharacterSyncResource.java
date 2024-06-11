package br.com.character.action;


import br.com.character.CharacterEntity;
import br.com.character.CharacterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.Map;

@Path("/v1/sync/{userId}")
public class CharacterSyncResource {


    @Inject
    private CharacterRepository characterRepository;

    @POST
    @Transactional
    public Response actionResolve(@RestPath("userId") Long id, Map<Object, Object> request) throws JsonProcessingException {
        try {
            Log.info(new ObjectMapper().writeValueAsString(request));
            var inventory = new ObjectMapper().readTree((String) request.get("data"))
                    .get("character").get("inventory").iterator();

            CharacterEntity character = characterRepository.findByUserId(id);
            inventory.forEachRemaining(it -> {
                        if (it.get("id").asText().contains("pocao_vermelha")) {
                            int quantity = it.get("quantity").asInt();
                            if (character.getPotions() > quantity) {
                                var potions = character.getPotions() - quantity;
                                character.setVitality(character.getVitality() + potions);
                                character.setPotions(quantity);
                            }
                        }
                    }
            );
            characterRepository.persist(character);
        } catch (Exception e) {
            Log.info("erro sync: ".concat(e.getMessage()));
        }
        return Response.ok().build();
    }
}
