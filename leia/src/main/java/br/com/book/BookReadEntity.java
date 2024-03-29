package br.com.book;

import br.com.book.note.NotesEntity;
import br.com.user.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class BookReadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Column
    private Integer pagesRead;

    @Column
    private Integer chapter;

    @Column
    private Boolean challengeAnswered;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "note_id")
    private List<NotesEntity> note;

    public List<NotesEntity> getNote() {
        return note;
    }

    public BookReadEntity setNote(List<NotesEntity> note) {
        this.note = note;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public BookReadEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Integer getChapter() {
        return chapter;
    }

    public BookReadEntity setChapter(Integer chapter) {
        this.chapter = chapter;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BookReadEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public BookEntity getBook() {
        return book;
    }

    public BookReadEntity setBook(BookEntity book) {
        this.book = book;
        return this;
    }

    public Integer getPagesRead() {
        return pagesRead;
    }

    public BookReadEntity setPagesRead(Integer pagesRead) {
        this.pagesRead = pagesRead;
        return this;
    }

    public Boolean getChallengeAnswered() {
        return challengeAnswered;
    }

    public BookReadEntity setChallengeAnswered(Boolean challengeAnswered) {
        this.challengeAnswered = challengeAnswered;
        return this;
    }
}
