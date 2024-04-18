package br.com.shared.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public class MemoryWrapper {

    @JsonProperty("pairPositions")
    List<MemoryGame> games;

    public List<MemoryGame> getGames() {
        return games;
    }

    public void setGames(List<MemoryGame> memoryGames) {
        this.games = memoryGames;
    }
}
