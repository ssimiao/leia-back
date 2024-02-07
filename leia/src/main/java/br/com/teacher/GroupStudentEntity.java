package br.com.teacher;

import br.com.character.CharacterEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class GroupStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long owner;

    @Column
    private String groupName;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    private List<CharacterEntity> student;

    public GroupStudentEntity() {
    }

    public GroupStudentEntity(Long owner, String groupName, List<CharacterEntity> student) {
        this.owner = owner;
        this.student = student;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public List<CharacterEntity> getStudent() {
        return student;
    }

    public void setStudent(List<CharacterEntity> student) {
        this.student = student;
    }
}
