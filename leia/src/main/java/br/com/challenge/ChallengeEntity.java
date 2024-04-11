package br.com.challenge;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class ChallengeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String challengeType;

    @Column(length = 3000)
    private String challenge;
    
    public Long getId() {
        return id;
    }

    public ChallengeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public ChallengeEntity setChallengeType(String challengeType) {
        this.challengeType = challengeType;
        return this;
    }

    public String getChallenge() {
        return challenge;
    }

    public ChallengeEntity setChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }
}
