package br.com.character.race;

import br.com.character.JsonValueName;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/v1/races")
public class RaceResource {

    @Inject
    private RaceRepository raceRepository;

    @POST
    @Transactional
    public Response saveRace(RaceRequest request) {
        raceRepository.persist(new RaceEntity(request.getName(), request.getColor()));
        return Response.ok().build();
    }

    @GET
    public Response getRaces() {
        return Response.ok(raceRepository.findAll().stream().toList()).build();
    }
}
