package dev.nacho.ghub.config;

import dev.nacho.ghub.domain.model.Usuario;
import dev.nacho.ghub.domain.model.enumeration.RolUsuario;
import dev.nacho.ghub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepo;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);

        // Google usa "sub" como identificador único
        String googleId = oauthUser.getAttribute("sub");
        String email    = oauthUser.getAttribute("email");
        String nombre   = oauthUser.getAttribute("name");

        // Busca o crea el usuario en tu BBDD
        Usuario user = usuarioRepo.findByGoogleId(googleId)
                .orElseGet(() -> {
                    Usuario u = Usuario.builder()
                            .id(UUID.randomUUID())
                            .googleId(googleId)
                            .email(email)
                            .nombreUsuario(nombre)
                            .rol(RolUsuario.USUARIO)
                            .fechaRegistro(LocalDateTime.now())
                            .build();
                    return usuarioRepo.save(u);
                });

        // Si ya existía pero cambió el email o nombre, actualízalo
        boolean updated = false;
        if (!user.getEmail().equals(email)) {
            user.setEmail(email);
            updated = true;
        }
        if (!user.getNombreUsuario().equals(nombre)) {
            user.setNombreUsuario(nombre);
            updated = true;
        }
        if (updated) {
            usuarioRepo.save(user);
        }

        return oauthUser;
    }
}