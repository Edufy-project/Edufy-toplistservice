package edufy.edufytoplistservice.controllers;

import edufy.edufytoplistservice.dto.ToplistDTO;
import edufy.edufytoplistservice.services.ToplistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("edufy/api/toplist")
public class ToplistController {

    private final ToplistService toplistService;

    public ToplistController(ToplistService toplistService) {
        this.toplistService = toplistService;
    }

    // Samlad 10-topplista
    @GetMapping("/mostplayed")
    public List<ToplistDTO> getMostPlayed() {
        return toplistService.getTopPlayedMedia();
    }

    // filtrerad 10-topplista baserat på medie typ
    @GetMapping("/mostplayed/{type}")
    public List<ToplistDTO> getMostPlayedByType(@PathVariable String type) {
        return toplistService.getTopPlayedMediaByType(type);
    }
}