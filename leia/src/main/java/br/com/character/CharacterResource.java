package br.com.character;

import br.com.character.classe.ClasseEntity;
import br.com.character.race.RaceEntity;
import br.com.user.UserEntity;
import br.com.user.UserRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

@Path("/v1/users/{userId}/characters")
public class CharacterResource {

    @Inject
    private CharacterRepository characterRepository;

    @POST
    public Response saveCharacter(@RestPath Long userId, CharacterRequest request) {
        ClasseEntity classe = new ClasseEntity(1L);
        RaceEntity race = new RaceEntity(request.getRaceId());
        UserEntity user = new UserEntity(userId);
        CharacterEntity character = new CharacterEntity(classe, race, user, request.getName());
        save(character);

        return Response.ok().build();
    }

    @Transactional
    public void save(CharacterEntity character) {
        characterRepository.persist(character);
    }

    @GET
    public Response getCharacter() {
        return Response.ok(characterRepository.findAll().stream().toList()).build();
    }
}
