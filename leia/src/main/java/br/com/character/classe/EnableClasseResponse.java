package br.com.character.classe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class EnableClasseResponse {

    private List<String> attributesCheck;

    private List<String> attributesNoCheck;

    private boolean blocked;

    private String classe;


    public EnableClasseResponse(String classe) {
        this.attributesCheck = new ArrayList<>();
        this.attributesNoCheck = new ArrayList<>();
        this.classe = classe;
    }

    public List<String> getAttributesCheck() {
        return attributesCheck;
    }

    public List<String> getAttributesNoCheck() {
        return attributesNoCheck;
    }

    public boolean isBlocked() {
        return !attributesNoCheck.isEmpty();
    }

    public String getClasse() {
        return classe;
    }
}
