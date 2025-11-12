package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class ToplistClient {

    private RestClient userServiceClient;
    private RestClient mediaServiceClient;

    public ToplistClient(RestClient.Builder restClientBuilder,
                            @Value("http://localhost:9093") String userServiceUrl,
                            @Value("http://localhost:9091") String mediaServiceUrl) {
        this.userServiceClient = restClientBuilder
                .baseUrl(userServiceUrl)
                .build();

        this.mediaServiceClient = restClientBuilder
                .baseUrl(mediaServiceUrl)
                .build();
    }

    // Hämtar alla användare från UserService API
    public UserDTO[] fetchAllUsers() {
        try {
            return userServiceClient.get()
                    .uri( "/api/edufy/listusers")
                    .retrieve()
                    .body(UserDTO[].class);
        } catch (Exception e) {
            return new UserDTO[0];
        }
    }

    // Hämtar alla mediaobjekt från MediaPlayer API
    public MediaDTO[] fetchAllMedia() {
        try {
            return mediaServiceClient.get()
                    .uri("/edufy/api/mediaplayer/media/{mediaName}")
                    .retrieve()
                    .body(MediaDTO[].class);
        } catch (Exception e) {
            return new MediaDTO[0];
        }
    }
}