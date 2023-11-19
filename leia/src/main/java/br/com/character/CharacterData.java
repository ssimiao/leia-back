package br.com.character;

import br.com.character.classe.Classe;
import br.com.character.classe.ClasseEntity;
import br.com.character.race.Race;
import br.com.character.race.RaceEntity;
import br.com.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public CharacterEntity toDomain(UserEntity userEntity) {
        ClasseEntity classeEntity = new ClasseEntity(Classe.valueOf(classeId).getId());
        RaceEntity raceEntity = new RaceEntity(Race.valueOf(raceId).getId());
        return new CharacterEntity(
                classeEntity,
                raceEntity,
                userEntity,
                name
        );
    }
}
