package br.com.book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookRequest {

    private String isbn;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("pages_read")
    private Integer pagesRead;

    @JsonProperty("chapter")
    private Integer chapter;

    @JsonProperty("read_id")
    private Long readId;

    public Integer getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(Integer pagesRead) {
        this.pagesRead = pagesRead;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Long getReadId() {
        return readId;
    }

    public void setReadId(Long readId) {
        this.readId = readId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
