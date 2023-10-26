package br.com.character.classe;

import br.com.character.JsonValueName;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/v1/classes")
public class ClasseResource {

    @Inject
    private ClasseRepository classeRepository;

    @POST
    @Transactional
    public Response saveClasse(JsonValueName<String> request) {
        classeRepository.persist(new ClasseEntity(request.getName()));
        return Response.ok().build();
    }

    @GET
    public Response getRaces() {
        return Response.ok(classeRepository.findAll().stream().toList()).build();
    }
}
