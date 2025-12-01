package edufy.edufytoplistservice.services;

import edufy.edufytoplistservice.services.ToplistClient;
import edufy.edufytoplistservice.dto.MediaDTO;
import edufy.edufytoplistservice.dto.MediaReference;
import edufy.edufytoplistservice.dto.ToplistDTO;
import edufy.edufytoplistservice.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToplistServiceImplTest {

    @Mock
    private ToplistClient restClient;

    @InjectMocks
    private ToplistServiceImpl service;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    MediaDTO media(long id, String title, String type, String artist, String album, long plays) {
        return new MediaDTO(
                id, title, type, LocalDate.now(), null, null,
                null, album, artist, null, plays
        );
    }

    MediaReference ref(String type, Long id) {
        return new MediaReference(type, id);
    }

    @Test
    void testGetTopPlayedMedia() {

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(
                        media(1,"Song A","music","Artist1","Album1",300),
                        media(2,"Song B","music","Artist2","Album2",150),
                        media(3,"Video C","video","Creator1","-",50)
                ));

        List<ToplistDTO> result = service.getTopPlayedMedia("T");

        assertEquals(3, result.size());
        assertEquals("Song A", result.get(0).getTitle());
        assertEquals(300, result.get(0).getPlayCount());
        assertEquals(500, result.get(0).getTotalPlayCount());
    }

    @Test
    void testGetTopPlayedMediaThrowsWhenEmpty() {

        when(restClient.fetchAllMedia("T")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTopPlayedMedia("T"));
    }

    @Test
    void testGetTopPlayedMediaByType() {

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(
                        media(1,"Song A","music","A","Album A",200),
                        media(2,"Podcast B","pod","B","Pod 1",50),
                        media(3,"Song C","music","C","Album B",100)
                ));

        List<ToplistDTO> result = service.getTopPlayedMediaByType("music", "T");

        assertEquals(2, result.size());
        assertEquals("Song A", result.get(0).getTitle());
        assertEquals(200, result.get(0).getPlayCount());
        assertEquals(300, result.get(0).getTotalPlayCount());
    }

    @Test
    void testGetTopPlayedMediaByTypeThrowsWhenNoneFound() {

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(media(1,"B","pod","A","AA",20)));

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTopPlayedMediaByType("music","T"));
    }

    @Test
    void testGetTopPlayedMediaForUser() {

        when(restClient.fetchUserMediaHistory(3L,"T"))
                .thenReturn(List.of(ref("music",1L), ref("music",2L)));

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(
                        media(1,"Song A","music","Artist A","Album A",200),
                        media(2,"Song B","music","Artist B","Album B",150),
                        media(3,"Other","pod","C","X",10)
                ));

        List<ToplistDTO> result = service.getTopPlayedMediaForUser(3L,"T");

        assertEquals(2, result.size());
        assertEquals("Song A", result.get(0).getTitle());
        assertEquals(350, result.get(0).getTotalPlayCount()); // 200+150
    }

    @Test
    void testGetTopPlayedMediaForUserReturnsEmptyWhenNoMatch() {

        when(restClient.fetchUserMediaHistory(3L,"T"))
                .thenReturn(List.of(ref("music",5L))); // finns inte i media

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(media(1,"Other","pod","X","-",20)));

        assertTrue(service.getTopPlayedMediaForUser(3L,"T").isEmpty());
    }

    @Test
    void testGetTopPlayedMediaForUserThrowsWhenUserNull() {

        when(restClient.fetchUserMediaHistory(3L,"T")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTopPlayedMediaForUser(3L,"T"));
    }

    @Test
    void testGetTopPlayedMediaForUserByType() {

        when(restClient.fetchUserMediaHistory(5L,"T"))
                .thenReturn(List.of(ref("music",1L),ref("pod",3L),ref("music",2L)));

        when(restClient.fetchAllMedia("T"))
                .thenReturn(List.of(
                        media(1,"Song A","music","AA","Album A",100),
                        media(2,"Song B","music","BB","Album B",50),
                        media(3,"Podcast","pod","CC","-",200)
                ));

        List<ToplistDTO> result = service.getTopPlayedMediaForUserByType(5L,"music","T");

        assertEquals(2, result.size());
        assertEquals("Song A", result.get(0).getTitle());
        assertEquals(150, result.get(0).getTotalPlayCount());
    }
}