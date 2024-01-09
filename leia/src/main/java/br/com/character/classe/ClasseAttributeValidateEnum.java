package br.com.character.classe;

public enum ClasseAttributeValidateEnum {
    GUERREIRO(40,10,30,30,10,5, "guerreiro"),
    MAGO(10,10,30,10,30,40, "mago");

    private final Integer forceAttribute;

    private final Integer agilityAttribute;


    private final Integer dexAttribute;

    private final Integer resistenceAttribute;

    private final Integer charismaAttribute;

    private final Integer intelligenceAttribute;

    private final String nameClasse;

    ClasseAttributeValidateEnum(Integer forceAttribute, Integer agilityAttribute,
                                Integer dexAttribute, Integer resistenceAttribute, Integer charismaAttribute,
                                Integer intelligenceAttribute, String nameClasse) {
        this.forceAttribute = forceAttribute;
        this.agilityAttribute = agilityAttribute;
        this.dexAttribute = dexAttribute;
        this.resistenceAttribute = resistenceAttribute;
        this.charismaAttribute = charismaAttribute;
        this.intelligenceAttribute = intelligenceAttribute;
        this.nameClasse = nameClasse;
    }

    public Integer getForceAttribute() {
        return forceAttribute;
    }

    public Integer getAgilityAttribute() {
        return agilityAttribute;
    }

    public Integer getDexAttribute() {
        return dexAttribute;
    }

    public Integer getResistenceAttribute() {
        return resistenceAttribute;
    }

    public Integer getCharismaAttribute() {
        return charismaAttribute;
    }

    public Integer getIntelligenceAttribute() {
        return intelligenceAttribute;
    }

    public String getNameClasse() {
        return nameClasse;
    }
}
