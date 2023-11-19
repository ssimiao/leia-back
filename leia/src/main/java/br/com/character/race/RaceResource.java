package br.com.character.race;

import br.com.character.JsonValueName;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/races")
public class RaceResource {

    @Inject
    private RaceRepository raceRepository;

    @POST
    @Transactional
    public Response saveRace(List<RaceRequest> request) {
        List<RaceEntity> races = request.stream().map(i -> new RaceEntity(i.getName(), i.getColor())).toList();
        raceRepository.persist(races);
        return Response.ok().build();
    }

    @GET
    public Response getRaces() {
        return Response.ok(raceRepository.findAll().stream().toList()).build();
    }
}
