package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.MediaReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    // Hämtar alla mediaobjekt från MediaPlayer API
    public List<MediaDTO> fetchAllMedia(String token) {
        List<MediaDTO> allMedia = new ArrayList<>();
        try {
            String[] types = {"music", "pod", "video"};
            for (String type : types) {
                MediaDTO[] result = mediaServiceClient.get()
                        .uri("/edufy/api/mediaplayer/getmedia/all/{type}", type)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .body(MediaDTO[].class);

                System.out.println("Fetched " + (result != null ? result.length : 0) + " items for type: " + type);

                if (result != null) {
                    allMedia.addAll(Arrays.asList(result));
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching media: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
        System.out.println("Total media fetched: " + allMedia.size());
        return allMedia;
    }

    public List<MediaReference> fetchUserMediaHistory(Long userId, String token) {
        try {
            MediaReference[] response = userServiceClient.get()
                    .uri("/edufy/api/users/usermediahistory/{userid}", userId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(MediaReference[].class);
            System.out.println("Fetched user media history: " + Arrays.toString(response));
            return Arrays.asList(response);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}