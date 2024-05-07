package br.com.shared.mail;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.courier.com/send")
public interface MailClient {

    @POST
    void send(MailDto dto, @HeaderParam("Authorization") String authorization);
}
