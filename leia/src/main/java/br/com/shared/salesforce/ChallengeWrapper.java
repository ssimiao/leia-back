package br.com.shared.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public class ChallengeWrapper {

    @JsonProperty("games")
    private List<ChallengeTypo> game;

    public List<ChallengeTypo> getGame() {
        return game;
    }

    public void setGame(List<ChallengeTypo> game) {
        this.game = game;
    }
}
