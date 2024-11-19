package br.com.realtech.ancora.repository;

import br.com.realtech.ancora.service.AuthService;
import br.com.realtech.ancora.util.EnvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class SearchRepository {
    private final AuthService authService;
    private static final String searchUrl = EnvUtil.getEnv("SEARCH_URL");

    @Autowired
    public SearchRepository(AuthService authService) {
        this.authService = authService;
    }

    public String search(String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var token = authService.getAuthToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao obter token: " + response.body());
        }

        return response.body();
    }
}
