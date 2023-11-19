package br.com.character;

import br.com.shared.exception.CharacterException;
import br.com.shared.exception.ResourceNotFoundException;
import br.com.user.UserEntity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import static java.lang.Math.abs;

@Path("/v1/users/{userId}/characters")
public class CharacterResource {

    @Inject
    private CharacterRepository characterRepository;

    @GET
    public Response getCharacter(@RestPath Long userId) {
        CharacterEntity characterEntity = characterRepository.find("user", new UserEntity(userId)).firstResult();
        return Response.ok(characterEntity).build();
    }

    @PATCH
    @Transactional
    public Response updateCharacter(@RestPath Long userId, CharacterRequest request) {
        CharacterEntity characterEntity = characterRepository.find("user", new UserEntity(userId)).firstResult();

        if (characterEntity == null)
            throw new ResourceNotFoundException("Personagem não encontrado");

        int agilityDifference = abs(request.getAgilityAttribute() - characterEntity.getAgilityAttribute());
        int resistenceDifference = abs(request.getResistenceAttribute() - characterEntity.getResistenceAttribute());
        int forceDifference = abs(request.getForceAttribute() - characterEntity.getForceAttribute());
        int dexDifference = abs(request.getDexAttribute() - characterEntity.getDexAttribute());
        int intelligenceDifference = abs(request.getIntelligenceAttribute() - characterEntity.getIntelligenceAttribute());
        int charismaDifference = abs(request.getCharismaAttribute() - characterEntity.getCharismaAttribute());

        int difference = agilityDifference + resistenceDifference + forceDifference + dexDifference + intelligenceDifference + charismaDifference;

        if (difference > characterEntity.getEnableAttributePoints())
            throw new CharacterException("Erro ao atualizar atributos do personagem");

        characterEntity.setAgilityAttribute(request.getAgilityAttribute())
                .setIntelligenceAttribute(request.getIntelligenceAttribute())
                .setForceAttribute(request.getForceAttribute())
                .setCharismaAttribute(request.getCharismaAttribute())
                .setDexAttribute(request.getDexAttribute())
                .setResistenceAttribute(request.getResistenceAttribute())
                .setEnableAttributePoints(characterEntity.getEnableAttributePoints() - difference);

        characterRepository.persist(characterEntity);
        return Response.ok().build();
    }
}
