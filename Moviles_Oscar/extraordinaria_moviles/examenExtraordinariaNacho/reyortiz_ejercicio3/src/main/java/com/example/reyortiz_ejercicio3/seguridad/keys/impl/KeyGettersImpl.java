package com.example.reyortiz_ejercicio3.seguridad.keys.impl;



import com.example.reyortiz_ejercicio3.seguridad.keys.KeyGetters;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class KeyGettersImpl implements KeyGetters {
    private static final String KEYSTORE_PASS = "admin";

    public PublicKey getServerPublic() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ksLoad = KeyStore.getInstance("PKCS12");
        ksLoad.load(new FileInputStream("keystore.pfx"), KEYSTORE_PASS.toCharArray());
        X509Certificate certificate = (X509Certificate) ksLoad.getCertificate("server");

        return certificate.getPublicKey();
    }
    public PrivateKey getServerPrivate() throws KeyStoreException, IOException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException {

        KeyStore ksLoad = KeyStore.getInstance("PKCS12");
        ksLoad.load(new FileInputStream("keystore.pfx"), KEYSTORE_PASS.toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(KEYSTORE_PASS.toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry("server", passwordProtection);

        return privateKeyEntry.getPrivateKey();
    }
}
