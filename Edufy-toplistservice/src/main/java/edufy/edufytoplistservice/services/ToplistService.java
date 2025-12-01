package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.ToplistDTO;
import java.util.List;

public interface ToplistService {
    List<ToplistDTO> getTopPlayedMedia(String token);
    List<ToplistDTO> getTopPlayedMediaByType(String type, String token);
    List<ToplistDTO> getTopPlayedMediaForUser(Long userId, String token);
    List<ToplistDTO> getTopPlayedMediaForUserByType(Long userId, String type, String token);
}