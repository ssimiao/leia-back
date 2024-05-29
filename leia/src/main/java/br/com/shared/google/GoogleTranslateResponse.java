package br.com.shared.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleTranslateResponse {

    @JsonProperty("data")
    private GoogleTranslationResponse data;

    public GoogleTranslationResponse getData() {
        return data;
    }

    public void setData(GoogleTranslationResponse data) {
        this.data = data;
    }
}
