package edufy.edufytoplistservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MediaDTO {

    private Long id;
    private String title;
    private String type; //TODO "MUSIC", "POD", "VIDEO"
    private LocalDate releaseDate;
    private String streamUrl;
    private Integer albumOrder;
    private LocalDateTime createdAt;
    private String albumTitle;
    private List<String> artistNames;
    private List<String> genreNames;
    private Long playCount;

    public MediaDTO() {}

    public MediaDTO(Long id, String title, String type, LocalDate releaseDate, String streamUrl, Integer albumOrder, LocalDateTime createdAt, String albumTitle, List<String> artistNames, List<String> genreNames, Long playCount) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.releaseDate = releaseDate;
        this.streamUrl = streamUrl;
        this.albumOrder = albumOrder;
        this.createdAt = createdAt;
        this.albumTitle = albumTitle;
        this.artistNames = artistNames;
        this.genreNames = genreNames;
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

    public List<String> getArtistNames() {
        return artistNames;
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public Long getPlayCount() {
        return playCount;
    }
}