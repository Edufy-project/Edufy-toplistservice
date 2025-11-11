package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.ToplistDTO;

import java.util.List;

public interface ToplistService {
    List<ToplistDTO> getTopPlayedMedia();
    List<ToplistDTO> getTopPlayedMediaByType(String type);
}
