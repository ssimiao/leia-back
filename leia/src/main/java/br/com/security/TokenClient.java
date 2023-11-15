package br.com.security;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.quarkus.rest.client.reactive.ClientQueryParams;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.concurrent.CompletionStage;

@RegisterRestClient(baseUri = "https://login.salesforce.com/services/oauth2/token")
public interface TokenClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ClientQueryParam(name = "client_id", value = "${salesforce.client_id}")
    @ClientQueryParam(name = "client_secret", value = "${salesforce.client_secret}")
    @ClientQueryParam(name = "username", value = "${salesforce.username}")
    @ClientQueryParam(name = "password", value = "${salesforce.password}")
    @ClientQueryParam(name = "grant_type", value = "password")
    Token generateToken();
}
