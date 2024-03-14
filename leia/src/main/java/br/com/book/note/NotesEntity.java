package br.com.book.note;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class NotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String Description;

    @Column
    private String relatedPageChapter;

    public NotesEntity() {
    }

    public NotesEntity(String description, String relatedPageChapter) {
        Description = description;
        this.relatedPageChapter = relatedPageChapter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRelatedPageChapter() {
        return relatedPageChapter;
    }

    public void setRelatedPageChapter(String relatedPageChapter) {
        this.relatedPageChapter = relatedPageChapter;
    }
}
