package br.com.user;

import br.com.character.CharacterData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCharacterData {

    @JsonProperty("character")
    private CharacterData character;

    @JsonProperty("account")
    private UserData account;

    public CharacterData getCharacter() {
        return character;
    }

    public void setCharacter(CharacterData character) {
        this.character = character;
    }

    public UserData getAccount() {
        return account;
    }

    public void setAccount(UserData account) {
        this.account = account;
    }
}
