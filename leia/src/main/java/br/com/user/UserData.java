package br.com.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    @JsonProperty("id")
    @NotBlank
    private Long id;

    @JsonProperty("username")
    @NotBlank
    private String username;

    @JsonProperty("password")
    @NotBlank
    private String password;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("name")
    private String name;

    @JsonProperty("genre")
    private String gender;

    @JsonProperty("email")
    private String email;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("avatar_id")
    private Integer avatarId;

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity toDomain() {
        return new UserEntity(username, password, birthDate, name, gender, email, userType);
    }
}
