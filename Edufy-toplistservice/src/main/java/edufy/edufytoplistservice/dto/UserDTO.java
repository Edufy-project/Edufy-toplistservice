package edufy.edufytoplistservice.dto;

public class UserDTO {
    private Long id;
    private String username;
    private Long totalPlayCount;
    private String preferredGenres;
    private Long playCount;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, Long totalPlayCount, String preferredGenres, Long playCount) {
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
}