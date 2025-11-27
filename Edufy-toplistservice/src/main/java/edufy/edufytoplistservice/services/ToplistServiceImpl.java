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
    private List<ToplistDTO> generateToplist(List<MediaDTO> mediaList/*, List<MediaReference> userMediaHistory*/) {

        /*if (userMediaHistory != null) {
            List<Long> mediaIds = userMediaHistory.stream()
                    .map(MediaReference::getId)
                    .toList();
            mediaList = mediaList.stream()
                    .filter(m -> mediaIds.contains(m.getId()))
                    .toList();
        }*/

        List<MediaDTO> validMediaList = mediaList.stream()
                .filter(m -> m.getPlayCount() != null)
                .toList();

        if (validMediaList.isEmpty()) {
            return List.of();
        }

        Long totalPlays = validMediaList.stream()
                .mapToLong(MediaDTO::getPlayCount)
                .sum();
        System.out.println(totalPlays);

        List<ToplistDTO> toplist = mediaList.stream()
                                    .sorted(Comparator.comparingLong(MediaDTO::getPlayCount).reversed())
                                    .limit(10)
                                    .map(m -> new ToplistDTO(
                                            m.getTitle(),
                                            m.getMediaType(),
                                            Optional.ofNullable(m.getArtistNames()).filter(list -> !list.isEmpty()).orElse(List.of("Unknown")),
                                            m.getAlbumTitle(),
                                            m.getGenreNames(),
                                            m.getReleaseDate(),
                                            m.getPlayCount(),
                                            totalPlays
                                    ))
                                    .collect(Collectors.toList());

        System.out.println(toplist.size());
        return toplist;
    }

    // Intern helper för användartopplista med optional typfilter
    private List<ToplistDTO> generateUserToplist(Long userId, String type, String token) {
        List<MediaReference> userHistory = restClient.fetchUserMediaHistory(userId, token);
        List<MediaDTO> allMedia = restClient.fetchAllMedia(token);

        System.out.println("All media: " + allMedia.size());
        System.out.println("User history: " + userHistory.size());

        if (userHistory == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        if (userHistory.isEmpty()) {
            return List.of(); // Historiken är tom
        }

        userHistory.forEach(ref ->
                System.out.println("  - Type: '" + ref.getMediaType() + "', ID: " + ref.getMediaId())
        );
        List<MediaDTO> userMedia = userHistory.stream()
                .map(ref -> allMedia.stream()
                        .filter(m -> m.getMediaId().equals(ref.getMediaId()) &&
                                m.getMediaType().equalsIgnoreCase(ref.getMediaType()) &&
                                (type == null || m.getMediaType().equalsIgnoreCase(type)))
                        .findFirst()
                        .orElse(null))
                .filter(m -> m != null && m.getPlayCount() != null)
                .toList();

        System.out.println("user media list: " + userMedia.size());
        if (userMedia.isEmpty()) {
            return List.of(); // Ingen media i historiken hittades
        }
        List<MediaDTO> validMediaList = userMedia.stream()
                .filter(m -> m.getPlayCount() != null)
                .toList();

        System.out.println("valid media list: " + validMediaList.size());
        if (validMediaList.isEmpty()) {
            return List.of();
        }

        Long totalPlays = validMediaList.stream()
                .mapToLong(MediaDTO::getPlayCount)
                .sum();
        System.out.println("total plays: " + totalPlays);

        return userMedia.stream()
                .sorted(Comparator.comparingLong(MediaDTO::getPlayCount).reversed())
                .limit(10)
                .map(m -> new ToplistDTO(
                        m.getTitle(),
                        m.getMediaType(),
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
    public List<ToplistDTO> getTopPlayedMedia(String token) {
        List<MediaDTO> allMedia = restClient.fetchAllMedia(token);
        if (allMedia.isEmpty()) {
            throw new ResourceNotFoundException("Media", "all", "No media found");
        }
        return generateToplist(allMedia);
    }

    @Override
    public List<ToplistDTO> getTopPlayedMediaByType(String type, String token) {
        List<MediaDTO> filteredMedia = restClient.fetchAllMedia(token).stream()
                .filter(m -> m.getMediaType() != null && m.getMediaType().equalsIgnoreCase(type))
                .toList();
        //List<MediaDTO> filteredMedia = restClient.fetchAllMedia(token);

        System.out.println("All media from type " + type + ": " + filteredMedia);


        if (filteredMedia.isEmpty()) {
            throw new ResourceNotFoundException("Media", "type", type);
        }
        return generateToplist(filteredMedia);
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


