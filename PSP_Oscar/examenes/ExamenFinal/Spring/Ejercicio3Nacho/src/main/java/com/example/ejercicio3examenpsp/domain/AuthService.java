package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.error.ErrorObject;
import com.example.ejercicio3examenpsp.data.modelo.request.AuthenticationRequest;
import com.example.ejercicio3examenpsp.data.modelo.response.AuthenticationResponse;
import io.vavr.control.Either;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        }
        catch (AuthenticationException e) {
            throw new RuntimeException("Error al autenticar el usuario "+request.username()+", mas info: "+e.getMessage());
        }
        //si no hay excepcion, el usuario esta autenticado
        //realmente este metodo esta en CustomUserDetailsService
        var user = userDetailsService.loadUserByUsername(request.username());

        var jwtTokenEither = jwtService.generateToken(user.getUsername(), 25); //el token de acceso caduca en 25 segundos
        var refreshTokenEither = jwtService.generateRefreshToken(user.getUsername(), 48); //el token de refresco caduca en 48 horas

        if (jwtTokenEither.isRight() && refreshTokenEither.isRight()) {
            var jwtToken = jwtTokenEither.get();
            var refreshToken = refreshTokenEither.get();
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new RuntimeException("Error al generar el token, mas info: "
                    +jwtTokenEither.getLeft());
        }
    }

    public String refresh(String username) {
        Either<ErrorObject, String> refreshToken = jwtService.generateRefreshToken(username, 3000);//el token de refresco caduca en 50 minutos
        if (refreshToken.isRight())
            return refreshToken.get();
        else
            return refreshToken.getLeft().getMessage();
    }

    //saco los roles del usuario autenticado
    public List<String> getAuthenticatedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        return roles;
    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
