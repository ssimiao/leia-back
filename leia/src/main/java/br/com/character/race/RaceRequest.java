package br.com.character.race;

public class RaceRequest {

    private String name;


    private String color;

    public RaceRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public RaceRequest() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
