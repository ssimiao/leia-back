package br.com.shared.google;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleTranslationResponse {

    @JsonProperty("translations")
    private List<GoogleTextResponse> translations;

    public List<GoogleTextResponse> getTranslations() {
        return translations;
    }

    public void setTranslations(List<GoogleTextResponse> translations) {
        this.translations = translations;
    }
}
