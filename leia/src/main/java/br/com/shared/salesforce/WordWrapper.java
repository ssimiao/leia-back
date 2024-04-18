package br.com.shared.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public class WordWrapper {

    @JsonProperty("words")
    private List<WordGame> games;

    public List<WordGame> getGames() {
        return games;
    }

    public void setGames(List<WordGame> games) {
        this.games = games;
    }
}
