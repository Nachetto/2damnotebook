package com.example.ejercicio3examenpsp.seguridad;


import com.example.ejercicio3examenpsp.domain.JwtService;
import com.example.ejercicio3examenpsp.ui.exceptions.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (header != null) {
            String[] values = header.split(" ");
            if (values[0].equalsIgnoreCase("Bearer") && values.length > 1) {
                try {
                    Claims claims = jwtService.extractAllClaims(values[1]);

                    String rol = (String) claims.get("rol");//tiene que llamarse igual que en el token
                    String name = (String) claims.get("user");
                    UserDetails userDetails = User.builder()
                            .username(name)
                            .password("")
                            .roles(rol)
                            .build();


                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    //informas a spring que el usuario esta autenticado y le dices quien es y sus roles
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    filterChain.doFilter(request, response);

                } catch (ExpiredJwtException e){
                    //quizas llamar al servicio de refresco y que lo devuelva
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } catch (Exception e) {
                    throw new TokenException(e.getMessage());
                }
            }

        }


    }
}
