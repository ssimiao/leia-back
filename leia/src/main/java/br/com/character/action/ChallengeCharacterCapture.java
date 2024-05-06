package br.com.character.action;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengeCharacterCapture {

    @JsonProperty("result")
    private String winOrFail;

    public String getWinOrFail() {
        return winOrFail;
    }

    public void setWinOrFail(String winOrFail) {
        this.winOrFail = winOrFail;
    }
}
