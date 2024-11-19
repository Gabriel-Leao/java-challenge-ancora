package br.com.realtech.ancora.service;

import br.com.realtech.ancora.repository.AuthRepository;
import br.com.realtech.ancora.util.EnvUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class AuthService {
    private static String authToken;
    private final AuthRepository authRepository;

    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public String getAuthToken() throws IOException, InterruptedException {
        if (authToken != null) {
            return authToken;
        }

        String requestBody = buildRequestBody();
        String responseBody = authRepository.fetchAuthToken(requestBody);

        Map responseMap = new ObjectMapper().readValue(responseBody, Map.class);
        authToken = (String) responseMap.get("access_token");

        return authToken;
    }

    private String buildRequestBody() {
        return "client_id=" + EnvUtil.getEnv("CLIENT_ID") +
                "&client_secret=" + EnvUtil.getEnv("CLIENT_SECRET") +
                "&grant_type=" + EnvUtil.getEnv("GRANT_TYPE");
    }
}
