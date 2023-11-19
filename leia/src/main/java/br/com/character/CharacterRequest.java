package br.com.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("force_attribute")
    private Integer forceAttribute;

    @JsonProperty("agility_attribute")
    private Integer agilityAttribute;


    @JsonProperty("dexterity_attribute")
    private Integer dexAttribute;

    @JsonProperty("resistence_attribute")
    private Integer resistenceAttribute;

    @JsonProperty("charisma_attribute")
    private Integer charismaAttribute;

    @JsonProperty("intelligence_attribute")
    private Integer intelligenceAttribute;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getForceAttribute() {
        return forceAttribute;
    }

    public void setForceAttribute(Integer forceAttribute) {
        this.forceAttribute = forceAttribute;
    }

    public Integer getAgilityAttribute() {
        return agilityAttribute;
    }

    public void setAgilityAttribute(Integer agilityAttribute) {
        this.agilityAttribute = agilityAttribute;
    }

    public Integer getDexAttribute() {
        return dexAttribute;
    }

    public void setDexAttribute(Integer dexAttribute) {
        this.dexAttribute = dexAttribute;
    }

    public Integer getResistenceAttribute() {
        return resistenceAttribute;
    }

    public void setResistenceAttribute(Integer resistenceAttribute) {
        this.resistenceAttribute = resistenceAttribute;
    }

    public Integer getCharismaAttribute() {
        return charismaAttribute;
    }

    public void setCharismaAttribute(Integer charismaAttribute) {
        this.charismaAttribute = charismaAttribute;
    }

    public Integer getIntelligenceAttribute() {
        return intelligenceAttribute;
    }

    public void setIntelligenceAttribute(Integer intelligenceAttribute) {
        this.intelligenceAttribute = intelligenceAttribute;
    }
}
