package br.com.character.action;

import br.com.character.CharacterEntity;
import br.com.character.CharacterRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

@Path("/v1/users/{userId}/characters/action")
public class CharacterActionResource {

    @Inject
    private CharacterRepository characterRepository;

    @PATCH
    @Transactional
    public Response actionResolve(@RestPath Long userId) {
        CharacterEntity character = characterRepository.findByUserId(userId);
        if (character != null && character.getPotions() > 0)  {
            character.setPotions(character.getPotions() - 1);
            character.setVitality(character.getVitality() + 1);
            characterRepository.persist(character);
            return Response.ok(character).build();
        }

        return Response.serverError().build();
    }

    @PUT
    @Transactional
    public Response challengeCaptureData(@RestPath Long userId, ChallengeCharacterCapture request) {
        CharacterEntity character = characterRepository.findByUserId(userId);
        if (character != null) {
            switch (request.getWinOrFail()) {
                case "W" : character.setChallengeWin(character.getChallengeWin() + 1);
                case "F" : character.setChallengeFail(character.getChallengeFail() + 1);
            }

            characterRepository.persist(character);
            return Response.ok(character).build();
        }

        return Response.serverError().build();
    }
}
