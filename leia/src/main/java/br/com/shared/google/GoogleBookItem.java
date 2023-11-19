package br.com.shared.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookItem {

    private GoogleBookVolume volumeInfo;

    public GoogleBookVolume getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(GoogleBookVolume volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
