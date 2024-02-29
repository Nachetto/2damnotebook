package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.error.ErrorObject;
import com.example.ejercicio3examenpsp.data.modelo.Usuario;
import com.example.ejercicio3examenpsp.data.repositorios.UserRepository;
import com.example.ejercicio3examenpsp.utils.RandomBytesGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    private final UserRepository repository;
    private final RandomBytesGenerator generator;

    public JwtService(UserRepository repository, RandomBytesGenerator generator) {
        this.repository = repository;
        this.generator = generator;
    }

    //clave con la que se firma el token, antes se tiene que crear en MainKeyStore
    private Key clavePrivadaKeyStore() {
        String passwordString = "admin";
        char[] password = passwordString.toCharArray();
        try (FileInputStream fis = new FileInputStream("keystore.pfx")) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password);
            return keyStore.getKey("server", password);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Either<ErrorObject, String> generateToken(String username, int duration) {
        Either<ErrorObject, String> res;
        try {
            Usuario user = repository.findByUsername(username).get();
            String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .setExpiration(Date.from(LocalDateTime.now().plusSeconds(duration).atZone(ZoneId.systemDefault()).toInstant()))
                    .claim("user", user.getUsername())
                    .claim("rol", user.getRol())
                    .signWith(clavePrivadaKeyStore())
                    .compact();
            res = Either.right(token);
        } catch (Exception e) {
            ErrorObject error = new ErrorObject("Error al generar el token porque: "+ e.getMessage(), LocalDateTime.now());
            return Either.left(error);
        }
        return res;
    }

    public Either<ErrorObject, String> generateRefreshToken(String username, int duration) {
        Either<ErrorObject, String> res;
        try {
            Usuario user = repository.findByUsername(username).get();
            String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .setExpiration(Date.from(LocalDateTime.now().plusHours(duration).atZone(ZoneId.systemDefault()).toInstant()))
                    .claim("user", user.getUsername())
                    .claim("rol", user.getRol())
                    //.signWith(SignatureAlgorithm.valueOf(""), "pepe")
                    .signWith(clavePrivadaKeyStore())
                    .compact();
            res = Either.right(token);
        } catch (Exception e) {
            ErrorObject error = new ErrorObject("Error al generar el refresh token porque: "+ e.getMessage(), LocalDateTime.now());
            return Either.left(error);
        }
        return res;
    }




    //sacar las afirmaciones del token
    public Claims extractAllClaims(String token) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        return Jwts
                .parserBuilder()
                .setSigningKey(clavePrivadaKeyStore())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
