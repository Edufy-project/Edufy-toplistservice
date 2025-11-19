package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.MediaReference;
import org.springframework.beans.factory.annotation.Value;
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
                            @Value("http://localhost:9091") String userServiceUrl,
                            @Value("http://localhost:9093") String mediaServiceUrl) {
        this.userServiceClient = restClientBuilder
                .baseUrl(userServiceUrl)
                .build();

        this.mediaServiceClient = restClientBuilder
                .baseUrl(mediaServiceUrl)
                .build();
    }

    // Hämtar alla mediaobjekt från MediaPlayer API
    public List<MediaDTO> fetchAllMedia() {
        List<MediaDTO> allMedia = new ArrayList<>();
        try {
            String[] types = {"music", "pod", "video"};
            for (String type : types) {
                MediaDTO[] result = mediaServiceClient.get()
                        .uri("/edufy/api/mediaplayer/getmedia/all/{type}", type)
                        .retrieve()
                        .body(MediaDTO[].class);
                if (result != null) {
                    allMedia.addAll(Arrays.asList(result));
                }
            }
        } catch (Exception e) {
            return List.of();
        }
        return allMedia;
    }

    public List<MediaReference> fetchUserMediaHistory(Long userId) {
        try {
            MediaReference[] response = userServiceClient.get()
                    .uri("/edufy/api/usermediahistory/{userid}", userId)
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