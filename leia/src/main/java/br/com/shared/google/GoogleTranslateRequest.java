package br.com.shared.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleTranslateRequest {

    @JsonProperty("q")
    private String text;

    @JsonProperty("target")
    private final String target = "pt-br";

    @JsonProperty("format")
    private final String format = "text";

    public GoogleTranslateRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTarget() {
        return target;
    }

    public String getFormat() {
        return format;
    }
}
