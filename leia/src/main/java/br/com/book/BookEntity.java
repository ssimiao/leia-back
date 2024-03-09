package br.com.book;

import br.com.challenge.ChallengeEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String isbn;

    @Column
    private String isbn13;


    @Column
    private Integer numberOfRecommendation;

    @Column
    private Integer rating;

    //if numberRecomendation === 1000 and rating is 4, then a new rating came and your add 4/1000 * new + 4

    @Column
    private Integer pages;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "challenge_id")
    private List<ChallengeEntity> challenge;

    @Column
    private String category;

    @Column
    private Boolean groupOnly;

    private Integer numberOfReaders;

    private Integer finishReaders;

    public Integer getFinishReaders() {
        return finishReaders == null ? 0 : finishReaders;
    }

    public void setFinishReaders(Integer finishReaders) {
        this.finishReaders = finishReaders;
    }

    public Integer getNumberOfReaders() {
        return numberOfReaders;
    }

    public void setNumberOfReaders(Integer numberOfReaders) {
        this.numberOfReaders = numberOfReaders;
    }

    public Boolean getGroupOnly() {
        return groupOnly;
    }

    public BookEntity setGroupOnly(Boolean groupOnly) {
        this.groupOnly = groupOnly;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public BookEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public Long getId() {
        return id;
    }

    public List<ChallengeEntity> getChallenge() {
        return challenge;
    }

    public BookEntity setChallenge(List<ChallengeEntity> challenge) {
        if (this.challenge == null)
            this.challenge = challenge;
        else
            this.challenge.addAll(challenge);

        return this;
    }

    public BookEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BookEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookEntity setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public BookEntity setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
        return this;
    }

    public Integer getNumberOfRecommendation() {
        return numberOfRecommendation;
    }

    public BookEntity setNumberOfRecommendation(Integer numberOfRecommendation) {
        this.numberOfRecommendation = numberOfRecommendation;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public BookEntity setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getPages() {
        return pages;
    }

    public BookEntity setPages(Integer pages) {
        this.pages = pages;
        return this;
    }
}
