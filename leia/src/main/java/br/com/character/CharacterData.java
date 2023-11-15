package br.com.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterData {

    @JsonProperty("classId")
    private String classeId;

    @JsonProperty("raceId")
    private String raceId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("colorId")
    private String color;

    public String getClasseId() {
        return classeId;
    }

    public void setClasseId(String classeId) {
        this.classeId = classeId;
    }

    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
