package edufy.edufytoplistservice.dto;

import java.time.LocalDate;
import java.util.List;

public class ToplistDTO {
    private String title;
    private String type; // MUSIC, POD, VIDEO
    private String artist;
    private String albumTitle;
    private String genre;
    private LocalDate releaseDate;
    private Long playCount;
    private Long totalPlayCount;

    public ToplistDTO() {
    }

    public ToplistDTO(String title, String type, String artist, String albumTitle, String genre, LocalDate releaseDate, Long playCount, Long totalPlayCount) {
        this.title = title;
        this.type = type;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.playCount = playCount;
        this.totalPlayCount = totalPlayCount;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public Long getTotalPlayCount() {
        return totalPlayCount;
    }
}

