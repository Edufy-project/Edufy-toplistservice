package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.*;
import edufy.edufytoplistservice.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToplistServiceImpl implements ToplistService {

    private final ToplistClient restClient;

    public ToplistServiceImpl(ToplistClient restClient) {
        this.restClient = restClient;}

    private List<ToplistDTO> generateToplist(List<MediaDTO> mediaList) {

        long totalPlays = mediaList.stream()
                .mapToLong(MediaDTO::getPlayCount)
                .sum();

        return mediaList.stream()
                .sorted(Comparator.comparingLong(MediaDTO::getPlayCount).reversed())
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getType(),
                        m.getArtistName(),
                        m.getAlbumTitle(),
                        m.getGenreName(),
                        m.getReleaseDate(),
                        m.getPlayCount(),
                        totalPlays
                ))
                .collect(Collectors.toList());
    }

    private List<ToplistDTO> generateUserToplist(Long userId, String type, String token) {
        List<MediaReference> userHistory = restClient.fetchUserMediaHistory(userId, token);
        List<MediaDTO> allMedia = restClient.fetchAllMedia(token);

        if (userHistory == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }
        if (userHistory.isEmpty()) {
            return List.of();
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
            return List.of();
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
                        m.getArtistName(),
                        m.getAlbumTitle(),
                        m.getGenreName(),
                        m.getReleaseDate(),
                        m.getPlayCount(),
                        totalPlays
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ToplistDTO> getTopPlayedMedia(String token) {
        List<MediaDTO> allMedia = restClient.fetchAllMedia(token);
        if (allMedia.isEmpty()) {
            throw new ResourceNotFoundException("Mediaplayer toplist", "Media", "No media found in system");
        }
        return generateToplist(allMedia);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaByType(String type, String token) {
        List<MediaDTO> filtered = restClient.fetchAllMedia(token).stream()
                .filter(m -> m.getType().equalsIgnoreCase(type))
                .toList();
        if (filtered.isEmpty()) {
            throw new ResourceNotFoundException("Media", "type", type);
        }
        return generateToplist(filtered);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaForUser(Long userId, String token) {
        return generateUserToplist(userId, null, token);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaForUserByType(Long userId, String type, String token) {
        return generateUserToplist(userId, type, token);
    }
}