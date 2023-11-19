package br.com.shared.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookVolume {

    private BigDecimal averageRating;

    private Long ratingsCount;

    private String title;

    private Integer pageCount;

    private List<String> categories;

    private List<GoogleBookIsbn> industryIdentifiers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<GoogleBookIsbn> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<GoogleBookIsbn> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

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
