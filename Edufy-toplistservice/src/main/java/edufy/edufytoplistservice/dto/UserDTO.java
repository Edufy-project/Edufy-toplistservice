package edufy.edufytoplistservice.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private Long totalPlayCount;
    private String preferredGenres;
    private Long playCount;
    private List<MediaReference> mediaHistory;


    public UserDTO() {
    }

    public UserDTO(Long id, String username, Long totalPlayCount, String preferredGenres, Long playCount,  List<MediaReference> mediaHistory) {
        this.id = id;
        this.username = username;
        this.totalPlayCount = totalPlayCount;
        this.preferredGenres = preferredGenres;
        this.playCount = playCount;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getTotalPlayCount() {
        return totalPlayCount;
    }

    public String getPreferredGenres() {
        return preferredGenres;
    }

    public Long getPlayCount() {
        return playCount;
    }
    public List<MediaReference> getMediaHistory() {
        return mediaHistory;
    }
}