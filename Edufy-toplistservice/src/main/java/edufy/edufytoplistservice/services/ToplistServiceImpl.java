package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.*;
import edufy.edufytoplistservice.exceptions.InvalidRequestException;
import edufy.edufytoplistservice.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ToplistServiceImpl implements ToplistService {

    private final ToplistClient restClient;

    public ToplistServiceImpl(ToplistClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<ToplistDTO> getTopPlayedMedia() {
        UserDTO[] users = restClient.fetchAllUsers();
        MediaDTO[] media = restClient.fetchAllMedia();

        if (users == null || users.length == 0) {
            throw new ResourceNotFoundException("Users", "all", "No users found");
        }
        if (media == null || media.length == 0) {
            throw new ResourceNotFoundException("Media", "all", "No media found");
        }

        // Summan av alla användares totalPlayCount
        Long totalPlays = Arrays.stream(users)
                .mapToLong(UserDTO::getTotalPlayCount)
                .sum();

        if (totalPlays == 0) {
            throw new InvalidRequestException("No play counts found for any users");
        }

        // Samlad topp 10 oavsett typ
        return Arrays.stream(media)
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getType(),
                        Optional.ofNullable(m.getArtistNames())
                                .filter(list -> !list.isEmpty())
                                .orElse(List.of("Unknown")),
                        m.getAlbumTitle(),
                        m.getGenreNames(),
                        m.getReleaseDate(),
                        m.getPlayCount(), // spelning per media
                        totalPlays // summan av alla användares spelningar
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaByType(String type) {
        UserDTO[] users = restClient.fetchAllUsers();
        MediaDTO[] media = restClient.fetchAllMedia();

        if (users == null || users.length == 0) {
            throw new ResourceNotFoundException("Users", "all", "No users found");
        }
        if (media == null || media.length == 0) {
            throw new ResourceNotFoundException("Media", "all", "No media found");
        }

        Long totalPlays = Arrays.stream(users)
                .mapToLong(UserDTO::getTotalPlayCount)
                .sum();

        if (totalPlays == 0) {
            throw new InvalidRequestException("No play counts found for any users");
        }

        // Filtrera på type
        return Arrays.stream(media)
                .filter(m -> m.getType() != null && m.getType().equalsIgnoreCase(type))
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getType(),
                        Optional.ofNullable(m.getArtistNames())
                                .filter(list -> !list.isEmpty())
                                .orElse(List.of("Unknown")),
                        m.getAlbumTitle(),
                        m.getGenreNames(),
                        m.getReleaseDate(),
                        m.getPlayCount(),
                        totalPlays
                ))
                .collect(Collectors.toList());
    }
}

