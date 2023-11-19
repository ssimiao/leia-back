package br.com.user;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://leiadeveloper-dev-ed.develop.my.salesforce.com/services/apexrest/account")
public interface AccountRestClient {

    @POST
    Response saveUserAndChar(List<UserCharacterData> request, @HeaderParam("Authorization") String token);

    @GET
    List<UserCharacterData> getUser(@QueryParam("username") String username, @HeaderParam("Authorization") String token);

    @GET
    Response get(@QueryParam("username") String username, @HeaderParam("Authorization") String token);

}
