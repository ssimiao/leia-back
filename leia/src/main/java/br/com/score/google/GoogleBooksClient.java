package br.com.score.google;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://www.googleapis.com/books/v1/volumes")
public interface GoogleBooksClient {

    @GET
    GoogleBookData buscaLivroPorISBN(@QueryParam("q") String query);
}
