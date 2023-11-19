package br.com.challenge;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengeRequest {

    @JsonProperty("challenge_type")
    private String challengeType;

    private PuzzleRequest puzzle;

    private QuizRequest quiz;

    private SearchWordRequest search;

    private boolean auto;

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    public PuzzleRequest getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(PuzzleRequest puzzle) {
        this.puzzle = puzzle;
    }

    public QuizRequest getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizRequest quiz) {
        this.quiz = quiz;
    }

    public SearchWordRequest getSearch() {
        return search;
    }

    public void setSearch(SearchWordRequest search) {
        this.search = search;
    }

}
