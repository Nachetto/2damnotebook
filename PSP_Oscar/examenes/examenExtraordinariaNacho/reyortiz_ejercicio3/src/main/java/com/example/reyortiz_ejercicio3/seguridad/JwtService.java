package com.example.reyortiz_ejercicio3.seguridad;

import com.example.reyortiz_ejercicio3.data.EmpleadoRepository;
import com.example.reyortiz_ejercicio3.seguridad.keys.KeyGetters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyGetters keyGetters;

    public Claims extractAllClaims(String token) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        return keyGetters.getServerPublic();

    }

}
