package br.com.shared.google;

import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://translation.googleapis.com/language/translate/v2?key=AIzaSyAt1l16H8NZRRr9wZv-0C0urao7C9_F7VA")
public interface GoogleTranslate {

    @POST
    GoogleTranslateResponse translate(GoogleTranslateRequest request);
}
