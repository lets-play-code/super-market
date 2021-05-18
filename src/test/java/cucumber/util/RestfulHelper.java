package cucumber.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;

public class RestfulHelper {
    private static final String LOCALHOST = "http://localhost:";

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<String> emptyEntity = new HttpEntity<String>(null, headers);
    private int port;

    public static RestfulHelper connect(int port) {
        return new RestfulHelper(port);
    }

    public RestfulHelper(int port) {
        this.port = port;
    }

    public ResponseEntity<String> get(String url) {
        return exchange(url, HttpMethod.GET, emptyEntity);
    }

    public ResponseEntity<String> post(String url) {
        return require(HttpMethod.POST, url);
    }

    public ResponseEntity<String> post(String url, String data) {
        return require(HttpMethod.POST, url, data);
    }

    public ResponseEntity<String> require(HttpMethod method, String url) {
        return restTemplate.exchange(fullUrl(url), method, HttpEntity.EMPTY, String.class);
    }

    public ResponseEntity<String> require(HttpMethod method, String url, String body) {
        return restTemplate.exchange(fullUrl(url), method, entity(body), String.class);
    }

    private HttpEntity<String> entity(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(body, headers);
    }


    private ResponseEntity<String> exchange(String url, HttpMethod method, HttpEntity<String> entity) {
        return restTemplate.exchange(fullUrl(url), method, entity, String.class);
    }

    private String fullUrl(String entry) {
        return LOCALHOST + port + entry;
    }

}
