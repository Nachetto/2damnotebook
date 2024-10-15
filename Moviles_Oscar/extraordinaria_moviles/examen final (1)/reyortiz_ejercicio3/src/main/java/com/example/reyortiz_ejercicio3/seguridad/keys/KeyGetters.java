package com.example.reyortiz_ejercicio3.seguridad.keys;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public interface KeyGetters {
    PrivateKey getServerPrivate() throws KeyStoreException, IOException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException;
    PublicKey getServerPublic() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
}
