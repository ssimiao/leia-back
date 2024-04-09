package br.com.character;

import br.com.character.classe.ClasseAttributeValidateEnum;
import br.com.character.classe.ClasseEntity;
import br.com.character.classe.ClasseRepository;
import br.com.character.classe.EnableClasseData;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
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
                    EnableClasseData enableClasseResponse = new EnableClasseData(classeAttributes.getNameClasse());
                    List<String> attributesCheck = enableClasseResponse.getAttributesCheck();
                    List<String> attributesNoCheck = enableClasseResponse.getAttributesNoCheck();

                    addValidationClasses(character, classeAttributes, attributesCheck, attributesNoCheck);
                    return enableClasseResponse;
                })
                .toList();

        return Response.ok(validationFinished).build();
    }

    @PATCH
    @Transactional
    public Response saveClasseCharacter(@RestPath Long userId, EnableClasseData enableClasseResponse) {
        CharacterEntity character = characterRepository.findByUserId(userId);

        ClasseAttributeValidateEnum validateEnum = ClasseAttributeValidateEnum.valueOf(enableClasseResponse.getClasse().toUpperCase());

        addValidationClasses(character, validateEnum, enableClasseResponse.getAttributesCheck(), enableClasseResponse.getAttributesNoCheck());

        if (enableClasseResponse.isBlocked())
            return Response.serverError().build();

        ClasseEntity classe = classeRepository.findByNameIgnoreCase(enableClasseResponse.getClasse());
        character.setClasse(classe);
        character.setCoins(character.getCoins() - 50);
        characterRepository.persist(character);

        return Response.ok(character).build();
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
