package br.com.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    @JsonProperty("access_token")
    private String accessToken;

    private String id;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
