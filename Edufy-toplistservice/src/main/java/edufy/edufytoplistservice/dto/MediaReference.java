package edufy.edufytoplistservice.dto;


public class MediaReference {
    private String mediaType;
    private Long mediaId;

    public MediaReference() {}

    public MediaReference(String mediaType, Long mediaId) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }
}