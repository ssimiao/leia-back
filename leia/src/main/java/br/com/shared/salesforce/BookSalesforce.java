package br.com.shared.salesforce;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://leiadeveloper-dev-ed.develop.my.salesforce.com/services/apexrest/product")
public interface BookSalesforce {

    @GET
    ProductWrapper getBookSalesforce(@QueryParam("type") String type, @HeaderParam("Authorization") String token);
}
