package br.com.book.note;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotesRequest {

    @JsonProperty("id_read")
    private Long idRead;

    @JsonProperty("id_node")
    private Long idNote;

    @JsonProperty("description")
    private String description;

    @JsonProperty("relatedPageChapter")
    private String relatedPageChapter;

    public Long getIdNote() {
        return idNote;
    }

    public void setIdNote(Long idNote) {
        this.idNote = idNote;
    }

    public Long getIdRead() {
        return idRead;
    }

    public void setIdRead(Long idRead) {
        this.idRead = idRead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedPageChapter() {
        return relatedPageChapter;
    }

    public void setRelatedPageChapter(String relatedPageChapter) {
        this.relatedPageChapter = relatedPageChapter;
    }
}
