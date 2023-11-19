package br.com.challenge;

import java.util.List;

public class SearchWordRequest {

    private String hint;

    private List<String> words;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
