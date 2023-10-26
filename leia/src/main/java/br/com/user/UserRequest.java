package br.com.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
