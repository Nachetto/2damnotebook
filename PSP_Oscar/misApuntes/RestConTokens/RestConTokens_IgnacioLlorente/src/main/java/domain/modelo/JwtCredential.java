package domain.modelo;

import jakarta.security.enterprise.credential.Credential;
import lombok.Getter;

public class JwtCredential implements Credential {
    @Getter
    private final String token;

    public JwtCredential(String token) {
        this.token = token;
    }
}