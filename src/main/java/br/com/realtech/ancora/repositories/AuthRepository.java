package br.com.realtech.ancora.repositories;

import br.com.realtech.ancora.utils.EnvUtil;
import org.springframework.stereotype.Repository;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.io.IOException;

@Repository
public class AuthRepository {
    private static final String authUrl = EnvUtil.getEnv("AUTH_URL");

    public String fetchAuthToken(String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao obter token: " + response.body());
        }

        return response.body();
    }
}
