package web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.model.User;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RestService {

    static final String URL = "http://91.241.64.178:7081/api/users";
    private String cookie;
    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getUsers() {
        HttpEntity<User> entity = new HttpEntity<>(new User());
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, typeRef);
        List<User> users = responseEntity.getBody();

        cookie = responseEntity.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.set("Content-Type", "application/json");
        return users;
    }

    public String addUser(User user) {
        return run(HttpMethod.POST, user);
    }

    public String editUser(User user) {
        return run(HttpMethod.PUT, user);
    }

    public String deleteUser() {
        Map<String, Long> params = new HashMap<>();
        params.put("id", 3L);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, entity, String.class, params).getBody();
    }

    private String run(HttpMethod method, User user) {
        String json = "";
        try {
            json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        } catch (Exception e) {
            System.out.println("");
        }
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        String result = restTemplate.exchange(URL, method, entity, String.class).getBody();
        return result;
    }
}
