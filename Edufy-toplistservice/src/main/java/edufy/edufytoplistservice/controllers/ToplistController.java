package edufy.edufytoplistservice.controllers;

import edufy.edufytoplistservice.dto.ToplistDTO;
import edufy.edufytoplistservice.services.ToplistService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    public List<ToplistDTO> getMostPlayedMedia() {
        return toplistService.getTopPlayedMedia();
    }

    // Global topp 10 per typ
    @GetMapping("/mostplayed/{type}")
    public List<ToplistDTO> getMostPlayedMediaByType(@PathVariable String type) {
        return toplistService.getTopPlayedMediaByType(type);
    }

    // Topp 10 för en specifik användare
    @GetMapping("/user/mostplayed/{userId}")
    public List<ToplistDTO> getUserToplist(@PathVariable Long userId) {
        return toplistService.getTopPlayedMediaForUser(userId);
    }

    @GetMapping("/user/mostplayed/{type}/{userId}")
    public List<ToplistDTO> getUserToplistByType(@PathVariable Long userId, @PathVariable String type) {
        return toplistService.getTopPlayedMediaForUserByType(userId, type);
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