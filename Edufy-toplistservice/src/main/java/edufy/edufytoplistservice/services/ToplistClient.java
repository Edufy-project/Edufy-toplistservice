package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class ToplistClient {

    private final RestClient restClient;

    @Value("${services.edufy-userservice.url:http://localhost:8081}")
    private String userServiceUrl;

    @Value("${services.edufy-mediaplayer-service.url:http://localhost:8082}")
    private String mediaServiceUrl;

    public ToplistClient(RestClient.Builder restClientBuilder, @Value("http://localhost:8080") String url) {
        this.restClient = restClientBuilder
                .baseUrl(url)
                .build();
    }

    // Hämtar alla användare från UserService API
    public UserDTO[] fetchAllUsers() {
        try {
            return restClient.get()
                    .uri(userServiceUrl + "/api/edufy/listusers")
                    .retrieve()
                    .body(UserDTO[].class);
        } catch (Exception e) {
            // Hantera fel
            return new UserDTO[0];
        }
    }

    // Hämtar alla mediaobjekt från MediaPlayer API
    public MediaDTO[] fetchAllMedia() {
        try {
            return restClient.get()
                    .uri(mediaServiceUrl + "/edufy/api/mediaplayer/media/all")
                    .retrieve()
                    .body(MediaDTO[].class);
        } catch (Exception e) {
            // Hantera fel
            return new MediaDTO[0];
        }
    }
}