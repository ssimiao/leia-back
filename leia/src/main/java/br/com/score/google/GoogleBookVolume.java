package br.com.score.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookVolume {

    private BigDecimal averageRating;

    private Long ratingsCount;

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Long getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Long ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
