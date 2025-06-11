package com.example.reyortiz_ejercicio3.domain;

import com.example.reyortiz_ejercicio3.data.EmpleadoRepository;
import com.example.reyortiz_ejercicio3.data.modelo.Empleado;
import com.example.reyortiz_ejercicio3.domain.errores.TokenException;
import com.example.reyortiz_ejercicio3.domain.modelo.UserAuthorized;
import com.example.reyortiz_ejercicio3.domain.modelo.UserRequest;
import com.example.reyortiz_ejercicio3.seguridad.JwtService;
import com.example.reyortiz_ejercicio3.seguridad.keys.KeyGetters;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final JwtService jwtService;
    private final KeyGetters keyGetters;
    private final EmpleadoRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserAuthorized login(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.nombre(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Empleado user = repository.findByNombre(request.nombre()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
            PrivateKey key = keyGetters.getServerPrivate();
            String accessToken = Jwts.builder()
                    .setSubject("accessToken")
                    .setExpiration(Date.from(LocalDateTime.now().plusSeconds(300).atZone(ZoneId.systemDefault()).toInstant()))
                    .claim("nombre", user.getNombre())
                    .claim("rol", user.getRol())
                    .signWith(key).compact();

            return new UserAuthorized(accessToken);

        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }

    public List<Empleado> getEmpleados() {
        return repository.findAll();
    }

    public Empleado add(Empleado empleado) {
        Empleado u = new Empleado(0, empleado.getNombre(), empleado.getPassword(), empleado.getFechaNac(), empleado.getRol(), null);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repository.save(u);
    }
}
