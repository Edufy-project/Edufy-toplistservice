package edufy.edufytoplistservice.controllers;

import edufy.edufytoplistservice.dto.ToplistDTO;
import edufy.edufytoplistservice.services.ToplistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/mostplayed")
    public List<ToplistDTO> getMostPlayedMedia(@AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMedia(token);
    }

    @GetMapping("/mostplayed/{type}")
    public List<ToplistDTO> getMostPlayedMediaByType(@PathVariable String type, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaByType(type, token);
    }

    @GetMapping("/user/mostplayed/{userId}")
    public List<ToplistDTO> getUserToplist(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaForUser(userId, token);
    }

    @GetMapping("/user/mostplayed/{type}/{userId}")
    public List<ToplistDTO> getUserToplistByType(@PathVariable Long userId, @PathVariable String type, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaForUserByType(userId, type, token);
    }
}