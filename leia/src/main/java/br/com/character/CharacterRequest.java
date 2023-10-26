package br.com.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterRequest {

    @JsonProperty("classe_id")
    private Long classeId;

    @JsonProperty("race_id")
    private Long raceId;

    @JsonProperty("name")
    private String name;

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
