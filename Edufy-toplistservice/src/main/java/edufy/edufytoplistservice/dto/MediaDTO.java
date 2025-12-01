package edufy.edufytoplistservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MediaDTO {

    private Long id;
    private String title;
    private String type;
    private LocalDate releaseDate;
    private String streamUrl;
    private Integer albumOrder;
    private LocalDateTime createdAt;
    private String albumTitle;
    private String artistName;
    private String genreName;
    private long playCount;

    public MediaDTO() {}

    public MediaDTO(Long id, String title, String type, LocalDate releaseDate, String streamUrl, Integer albumOrder, LocalDateTime createdAt, String albumTitle, String artistName, String genreName, long playCount) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.releaseDate = releaseDate;
        this.streamUrl = streamUrl;
        this.albumOrder = albumOrder;
        this.createdAt = createdAt;
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.genreName = genreName;
        this.playCount = playCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public Integer getAlbumOrder() {
        return albumOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getGenreName() {
        return genreName;
    }

    public long getPlayCount() {
        return playCount;
    }
}