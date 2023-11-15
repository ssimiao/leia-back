package br.com.character;

import br.com.character.classe.ClasseEntity;
import br.com.user.UserEntity;
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
    public Response saveCharacter(@RestPath Long userId, CharacterData request) {
        ClasseEntity classe = new ClasseEntity(1L);
        UserEntity user = new UserEntity(userId);

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
