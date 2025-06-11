package jakarta.security;

import domain.modelo.JwtCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.extern.log4j.Log4j2;
import service.UsuarioService;


import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Log4j2
public class InMemoryIdentityStore implements IdentityStore {

    private final UsuarioService serviciosUsuarios;

    @Override
    public int priority() {
        return 10;
    }


    @Inject
    public InMemoryIdentityStore(UsuarioService serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }


    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof JwtCredential jwtCredential) {
            String token = jwtCredential.getToken();
            try {
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(serviciosUsuarios.getSecretKey())
                        .build()
                        .parseClaimsJws(token);

                // Valida el token con la clave secreta y comprueba que no esté caducado
                Claims claims = jws.getBody();
                String username = claims.get("user", String.class);
                String group = claims.get("group", String.class);
                String tipoDeToken = claims.get("tipo", String.class);

                // Comprueba que el token sea de tipo accesstoken
                if (!"accesstoken".equals(tipoDeToken)) {
                    return INVALID_RESULT;
                }

                // Si es valido le paso la mierda al securityContext
                return new CredentialValidationResult(username, new HashSet<>(Arrays.asList(group.split(","))));
            } catch (JwtException e) {
                log.error("Error validando el token, mas info: \n"+e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                log.error("Error al obtener la clave secreta porque: \n"+e.getMessage());
            }
        } else {
            throw new IllegalStateException("Valor inesperado: " + credential);
        }

        return INVALID_RESULT;
    }

}
