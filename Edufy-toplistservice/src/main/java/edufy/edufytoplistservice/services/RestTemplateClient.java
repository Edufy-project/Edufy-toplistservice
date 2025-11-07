package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    // Servicenamn som används med Eureka eller fallback till localhost(dev)
    @Value("${services.edufy-userservice.url:http://localhost:8081}")
    private String userServiceUrl;

    @Value("${services.edufy-mediaplayer-service.url:http://localhost:8082}")
    private String mediaServiceUrl;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Hämtar alla användare från UserService API
    public UserDTO[] fetchAllUsers() {
        return restTemplate.getForObject(
                userServiceUrl + "/api/edufy/listusers",
                UserDTO[].class
        );
    }

    // Hämtar alla mediaobjekt från MediaPlayer API
    public MediaDTO[] fetchAllMedia() {
        return restTemplate.getForObject(
                mediaServiceUrl + "/edufy/api/mediaplayer/media/all",
                MediaDTO[].class
        );
    }
}

