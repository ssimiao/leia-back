package br.com.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String birthDate;

    @Column
    private String name;

    @Column
    private String genre;

    @Column
    private String email;

    @Column
    private String userType;

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity(String username, String password, String birthDate, String name, String genre, String email, String userType) {
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.name = name;
        this.genre = genre;
        this.email = email;
        this.userType = userType;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
