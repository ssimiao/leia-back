package br.com.character.classe;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class ClasseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    public ClasseEntity() {
    }

    public ClasseEntity(String name) {
        this.name = name;
    }

    public ClasseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
