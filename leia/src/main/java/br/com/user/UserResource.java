package br.com.user;

import br.com.character.CharacterData;
import br.com.character.CharacterEntity;
import br.com.character.CharacterRepository;
import br.com.character.classe.ClasseEntity;
import br.com.character.classe.ClasseRepository;
import br.com.character.race.RaceEntity;
import br.com.character.race.RaceRepository;
import br.com.security.Token;
import br.com.security.TokenClient;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/v1/users")
public class UserResource {

    @Inject
    private UserRepository userRepository;

    @Inject
    private CharacterRepository characterRepository;

    @Inject
    private RaceRepository raceRepository;

    @Inject
    private ClasseRepository classeRepository;

    @RestClient
    private AccountRestClient accountRestClient;

    @RestClient
    private TokenClient tokenClient;

    @POST
    @Transactional
    public Response saveUser(Request<UserCharacterData> userRequest, @QueryParam("process") String process) {
        UserCharacterData userCharacterData = userRequest.getRequest();
        CharacterData character = userCharacterData.getCharacter();

        if (process.equalsIgnoreCase("salesforce")) {
            Token token = tokenClient.generateToken();
            Log.info(token.getAccessToken());
            Response response = accountRestClient.saveUserAndChar(List.of(userRequest.getRequest()), "Bearer " + token.getAccessToken());
            return Response.status(response.getStatus()).build();
        } else {
            if ("professor".equalsIgnoreCase(userCharacterData.getAccount().getUserType())){
                character.setClasseId("Professor");
                character.setRaceId("owl");
                character.setColor("brown");
            }
            else
                character.setClasseId("Novato");

            UserEntity domain = userCharacterData.getAccount().toDomain();
            userRepository.persist(domain);
            RaceEntity race = raceRepository.findByNameAndColor(character.getRaceId(), character.getColor());
            ClasseEntity classe = classeRepository.findByName(character.getClasseId());
            CharacterEntity characterEntity  = new CharacterEntity()
                    .setClasse(classe)
                    .setRace(race)
                    .setUser(domain)
                    .setName(character.getName())
                    .setLevel(1)
                    .setXp(0)
                    .setAgilityAttribute(0)
                    .setForceAttribute(0)
                    .setDexAttribute(0)
                    .setResistenceAttribute(0)
                    .setCharismaAttribute(0)
                    .setCoins(0)
                    .setEnableAttributePoints(10)
                    .setIntelligenceAttribute(0);
            characterRepository.persist(characterEntity);
            return Response.status(200).build();
        }
    }
}
