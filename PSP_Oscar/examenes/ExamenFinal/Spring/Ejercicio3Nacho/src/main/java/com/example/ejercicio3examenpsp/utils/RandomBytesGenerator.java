package com.example.ejercicio3examenpsp.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomBytesGenerator {

    //INFO: método para generar la KSA, la ksa es la clave que se usa para cifrar el mensaje
    //Lo usa spring security automaticamente, por eso esta en gris y no se usa en el codigo
    //lo usabamos tambien para el correo de verificacion, pero no ha entrado
    public String randomBytes() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }
}
