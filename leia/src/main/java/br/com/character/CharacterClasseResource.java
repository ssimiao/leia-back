package br.com.character;

import br.com.character.classe.ClasseAttributeValidateEnum;
import br.com.character.classe.ClasseEntity;
import br.com.character.classe.ClasseRepository;
import br.com.character.classe.EnableClasseResponse;
import br.com.user.UserEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/v1/users/{userId}/characters/classes")
public class CharacterClasseResource {

    @Inject
    private CharacterRepository characterRepository;

    @Inject
    private ClasseRepository classeRepository;

    /**
     * @param userId
     * @return classes to that user update
     */
    @GET
    public Response getCharacter(@RestPath Long userId) {
        CharacterEntity character = characterRepository.findByUserId(userId);

        var validationFinished = classeRepository.findAll()
                .stream()
                .filter(classe -> !"Professor".equalsIgnoreCase(classe.getName()) &&
                        !"Novato".equalsIgnoreCase(classe.getName()))
                .map(classe -> {
                    ClasseAttributeValidateEnum classeAttributes = ClasseAttributeValidateEnum.valueOf(classe.getName().toUpperCase());
                    EnableClasseResponse enableClasseResponse = new EnableClasseResponse(classeAttributes.getNameClasse());
                    List<String> attributesCheck = enableClasseResponse.getAttributesCheck();
                    List<String> attributesNoCheck = enableClasseResponse.getAttributesNoCheck();

                    addValidationClasses(character, classeAttributes, attributesCheck, attributesNoCheck);
                    return enableClasseResponse;
                })
                .toList();

        return Response.ok(validationFinished).build();
    }

    private static void addValidationClasses(CharacterEntity character, ClasseAttributeValidateEnum classeAttributes, List<String> attributesCheck, List<String> attributesNoCheck) {
        if (character.getDexAttribute() > classeAttributes.getDexAttribute()) {
            attributesCheck.add("destreza");
        } else {
            attributesNoCheck.add("destreza");
        }

        if (character.getCharismaAttribute() > classeAttributes.getCharismaAttribute()) {
            attributesCheck.add("carisma");
        } else {
            attributesNoCheck.add("carisma");
        }

        if (character.getForceAttribute() > classeAttributes.getForceAttribute()) {
            attributesCheck.add("força");
        } else {
            attributesNoCheck.add("força");
        }

        if (character.getAgilityAttribute() > classeAttributes.getAgilityAttribute()) {
            attributesCheck.add("agilidade");
        } else {
            attributesNoCheck.add("agilidade");
        }

        if (character.getIntelligenceAttribute() > classeAttributes.getIntelligenceAttribute()) {
            attributesCheck.add("inteligência");
        } else {
            attributesNoCheck.add("inteligência");
        }

        if (character.getResistenceAttribute() > classeAttributes.getResistenceAttribute()) {
            attributesCheck.add("resistência");
        } else {
            attributesNoCheck.add("resistência");
        }
    }
}
