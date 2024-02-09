package br.com.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassData {

    @JsonProperty("old_pass")
    private String oldPass;

    @JsonProperty("new_pass")
    private String newPass;

    @JsonProperty("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
