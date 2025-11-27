package edufy.edufytoplistservice.controllers;

import edufy.edufytoplistservice.dto.ToplistDTO;
import edufy.edufytoplistservice.services.ToplistService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

    // Global topp 10
    @GetMapping("/mostplayed")
    public List<ToplistDTO> getMostPlayedMedia(@AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMedia(token);
    }

    // Global topp 10 per typ
    @GetMapping("/mostplayed/{type}")
    public List<ToplistDTO> getMostPlayedMediaByType(@PathVariable String type, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaByType(type, token);
    }

    // Topp 10 för en specifik användare
    @GetMapping("/user/{userId}/mostplayed")
    public List<ToplistDTO> getUserToplist(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaForUser(userId, token);
    }

    @GetMapping("/user/{userId}/mostplayed/{type}")
    public List<ToplistDTO> getUserToplistByType(@PathVariable Long userId, @PathVariable String type, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return toplistService.getTopPlayedMediaForUserByType(userId, type, token);
    }

    // LISTOR MED SECURITY CONFIG ?
//    @GetMapping("/mostplayed")
//    public List<ToplistDTO> getTopPlayedForUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        return toplistService.getTopPlayedMediaForUsername(username);
//    }
//    @GetMapping("/mostplayed/{type}")
//    public List<ToplistDTO> getTopPlayedForUserByType(@PathVariable String type) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        return toplistService.getTopPlayedMediaForUsernameByType(username, type);
//    }
}