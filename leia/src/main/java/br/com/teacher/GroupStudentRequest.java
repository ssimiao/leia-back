package br.com.teacher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupStudentRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("characters_id")
    private List<Long> characters;

    @JsonProperty("isbn")
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Long> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Long> characters) {
        this.characters = characters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
