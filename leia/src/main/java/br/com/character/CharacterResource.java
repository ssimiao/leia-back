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

        if (request.getPath() != null) {
            isUpdatable(characterEntity, request.getValue());
            switch (request.getPath()) {
                case "dex_attribute" ->
                        characterEntity.setDexAttribute(characterEntity.getDexAttribute() + request.getValue());
                case "force_attribute" ->
                        characterEntity.setForceAttribute(characterEntity.getForceAttribute() + request.getValue());
                case "agility_attribute" -> characterEntity.setAgilityAttribute(characterEntity.getAgilityAttribute() + request.getValue());
                case "resistence_attribute" -> characterEntity.setResistenceAttribute(characterEntity.getResistenceAttribute() + request.getValue());
                case "charisma_attribute" -> characterEntity.setCharismaAttribute(characterEntity.getCharismaAttribute() + request.getValue());
                case "intelligence_attribute" -> characterEntity.setIntelligenceAttribute(characterEntity.getIntelligenceAttribute() + request.getValue());
                default -> throw new IllegalStateException("Unexpected value: " + request.getPath());
            }
            characterEntity.setEnableAttributePoints(characterEntity.getEnableAttributePoints() - request.getValue());
            characterRepository.persist(characterEntity);
            return Response.ok().build();
        }

        int agilityDifference = abs(request.getAgilityAttribute() - characterEntity.getAgilityAttribute());
        int resistenceDifference = abs(request.getResistenceAttribute() - characterEntity.getResistenceAttribute());
        int forceDifference = abs(request.getForceAttribute() - characterEntity.getForceAttribute());
        int dexDifference = abs(request.getDexAttribute() - characterEntity.getDexAttribute());
        int intelligenceDifference = abs(request.getIntelligenceAttribute() - characterEntity.getIntelligenceAttribute());
        int charismaDifference = abs(request.getCharismaAttribute() - characterEntity.getCharismaAttribute());

        int difference = agilityDifference + resistenceDifference + forceDifference + dexDifference + intelligenceDifference + charismaDifference;

        isUpdatable(characterEntity, difference);

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

    private static void isUpdatable(CharacterEntity characterEntity, int difference) {
        if (difference > characterEntity.getEnableAttributePoints())
            throw new CharacterException("Erro ao atualizar atributos do personagem");
    }
}
