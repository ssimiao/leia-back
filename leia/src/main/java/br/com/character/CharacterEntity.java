package br.com.character;

import br.com.character.classe.ClasseEntity;
import br.com.character.race.RaceEntity;
import br.com.user.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classe_id")
    private ClasseEntity classe;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "race_id")
    private RaceEntity race;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private String name;

    @Column
    private Integer level;

    @Column
    private Integer xp;

    @Column
    private Integer forceAttribute;

    @Column
    private Integer agilityAttribute;


    @Column
    private Integer dexAttribute;

    @Column
    private Integer resistenceAttribute;

    @Column
    private Integer charismaAttribute;

    @Column
    private Integer intelligenceAttribute;

    @Column
    private Integer coins;

    @Column
    private Integer enableAttributePoints;

    @Column
    private Integer vitality;

    @Column
    private Integer potions;

    @Column
    private Integer challengeWin;

    @Column
    private Integer challengeFail;

    public CharacterEntity() {
    }

    public Integer getPotions() {
        return potions == null ? 0 : potions;
    }

    public CharacterEntity setPotions(Integer potions) {
        this.potions = potions;
        return this;
    }

    public Integer getVitality() {
        return vitality == null ? 0 : vitality;
    }

    public CharacterEntity setVitality(Integer vitality) {
        this.vitality = vitality;
        return this;
    }

    public Integer getEnableAttributePoints() {
        return enableAttributePoints;
    }

    public CharacterEntity setEnableAttributePoints(Integer enableAttributePoints) {
        this.enableAttributePoints = enableAttributePoints;
        return this;
    }

    public CharacterEntity(ClasseEntity classe, RaceEntity race, UserEntity user, String name) {
        this.classe = classe;
        this.race = race;
        this.user = user;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public CharacterEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCoins() {
        return coins;
    }

    public CharacterEntity setCoins(Integer coins) {
        this.coins = coins;
        return this;
    }

    public ClasseEntity getClasse() {
        return classe;
    }

    public CharacterEntity setClasse(ClasseEntity classe) {
        this.classe = classe;
        return this;
    }

    public RaceEntity getRace() {
        return race;
    }

    public CharacterEntity setRace(RaceEntity race) {
        this.race = race;
        return this;
    }

    public String getName() {
        return name;
    }

    public CharacterEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CharacterEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public CharacterEntity setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getXp() {
        return xp;
    }

    public CharacterEntity setXp(Integer xp) {
        this.xp = xp;
        return this;
    }

    public Integer getXpToLevelUp() {
        return (level + 1) * 100 + (level * 100);
    }

    public Integer getForceAttribute() {
        return forceAttribute;
    }

    public CharacterEntity setForceAttribute(Integer forceAttribute) {
        this.forceAttribute = forceAttribute;
        return this;
    }

    public Integer getAgilityAttribute() {
        return agilityAttribute;
    }

    public CharacterEntity setAgilityAttribute(Integer agilityAttribute) {
        this.agilityAttribute = agilityAttribute;
        return this;
    }

    public Integer getDexAttribute() {
        return dexAttribute;
    }

    public CharacterEntity setDexAttribute(Integer dexAttribute) {
        this.dexAttribute = dexAttribute;
        return this;
    }

    public Integer getResistenceAttribute() {
        return resistenceAttribute;
    }

    public CharacterEntity setResistenceAttribute(Integer resistenceAttribute) {
        this.resistenceAttribute = resistenceAttribute;
        return this;
    }

    public Integer getCharismaAttribute() {
        return charismaAttribute;
    }

    public CharacterEntity setCharismaAttribute(Integer charismaAttribute) {
        this.charismaAttribute = charismaAttribute;
        return this;
    }

    public Integer getIntelligenceAttribute() {
        return intelligenceAttribute;
    }

    public CharacterEntity setIntelligenceAttribute(Integer intelligenceAttribute) {
        this.intelligenceAttribute = intelligenceAttribute;
        return this;
    }

    public Integer getChallengeWin() {
        return challengeWin == null ? 0 : challengeWin;
    }

    public void setChallengeWin(Integer challengeWin) {
        this.challengeWin = challengeWin;
    }

    public Integer getChallengeFail() {
        return challengeFail == null ? 0 : challengeFail;
    }

    public void setChallengeFail(Integer challengeFail) {
        this.challengeFail = challengeFail;
    }
}
