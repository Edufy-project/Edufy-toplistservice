package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.*;
import edufy.edufytoplistservice.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToplistServiceImpl implements ToplistService {

    private final ToplistClient restClient;

    public ToplistServiceImpl(ToplistClient restClient) {
        this.restClient = restClient;}


    // Gemensam metod för att generera topp 10 lista
    private List<ToplistDTO> generateToplist(List<MediaDTO> mediaList, List<MediaReference> userMediaHistory) {
        if (userMediaHistory != null) {
            List<Long> mediaIds = userMediaHistory.stream()
                    .map(MediaReference::getMediaId)
                    .toList();
            mediaList = mediaList.stream()
                    .filter(m -> mediaIds.contains(m.getId()))
                    .toList();
        }

        long totalPlays = mediaList.stream()
                .mapToLong(MediaDTO::getPlayCount)
                .sum();

        if (totalPlays == 0) {
            return List.of();
        }

        return mediaList.stream()
                .sorted(Comparator.comparingLong(MediaDTO::getPlayCount).reversed())
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getType(),
                        Optional.ofNullable(m.getArtistNames()).filter(list -> !list.isEmpty()).orElse(List.of("Unknown")),
                        m.getAlbumTitle(),
                        m.getGenreNames(),
                        m.getReleaseDate(),
                        m.getPlayCount(),
                        totalPlays
                ))
                .collect(Collectors.toList());
    }

    // Intern helper för användartopplista med optional typfilter
    private List<ToplistDTO> generateUserToplist(Long userId, String type) {
        List<MediaReference> userHistory = restClient.fetchUserMediaHistory(userId);
        List<MediaDTO> allMedia = restClient.fetchAllMedia();

        if (userHistory == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        if (userHistory.isEmpty()) {
            return List.of(); // Historiken är tom
        }

        List<MediaDTO> userMedia = userHistory.stream()
                .map(ref -> allMedia.stream()
                        .filter(m -> m.getId().equals(ref.getMediaId()) &&
                                m.getType().equalsIgnoreCase(ref.getMediaType()) &&
                                (type == null || m.getType().equalsIgnoreCase(type)))
                        .findFirst()
                        .orElse(null))
                .filter(m -> m != null)
                .toList();

        if (userMedia.isEmpty()) {
            return List.of(); // Ingen media i historiken hittades
        }
        long totalPlays = userMedia.stream()
                .mapToLong(MediaDTO::getPlayCount)
                .sum();

        return userMedia.stream()
                .sorted(Comparator.comparingLong(MediaDTO::getPlayCount).reversed())
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getType(),
                        Optional.ofNullable(m.getArtistNames()).filter(list -> !list.isEmpty()).orElse(List.of("Unknown")),
                        m.getAlbumTitle(),
                        m.getGenreNames(),
                        m.getReleaseDate(),
                        m.getPlayCount(),
                        totalPlays
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ToplistDTO> getTopPlayedMedia() {
        List<MediaDTO> allMedia = restClient.fetchAllMedia();
        if (allMedia.isEmpty()) {
            throw new ResourceNotFoundException("Media", "all", "No media found");
        }
        return generateToplist(allMedia, null);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaByType(String type) {
        List<MediaDTO> filteredMedia = restClient.fetchAllMedia().stream()
                .filter(m -> m.getType() != null && m.getType().equalsIgnoreCase(type))
                .toList();
        if (filteredMedia.isEmpty()) {
            throw new ResourceNotFoundException("Media", "type", type);
        }
        return generateToplist(filteredMedia, null);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaForUser(Long userId) {
        return generateUserToplist(userId, null);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaForUserByType(Long userId, String type) {
        return generateUserToplist(userId, type);
    }
}


