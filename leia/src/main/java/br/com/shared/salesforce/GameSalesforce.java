package br.com.shared.salesforce;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@RegisterRestClient(baseUri = "https://leiadeveloper-dev-ed.develop.my.salesforce.com/services/apexrest/game")
public interface GameSalesforce {

    @GET
    ChallengeWrapper getGamesFromIsbn(@QueryParam("isbn") String isbn, @HeaderParam("Authorization") String token);
}
