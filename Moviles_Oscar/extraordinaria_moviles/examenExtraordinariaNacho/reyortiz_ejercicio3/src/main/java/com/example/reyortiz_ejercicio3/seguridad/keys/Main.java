package com.example.reyortiz_ejercicio3.seguridad.keys;

import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        //GENERATE SERVER KEYS
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPairRSA = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPairRSA.getPrivate();
        PublicKey publicKey = keyPairRSA.getPublic();


        //SET CERTIFICATE VALUES
        X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();
        certificateGenerator.setSerialNumber(BigInteger.valueOf(1));
        certificateGenerator.setSubjectDN(new X509Principal("CN=Server"));
        certificateGenerator.setIssuerDN(new X509Principal("CN=Server"));
        certificateGenerator.setPublicKey(publicKey);
        certificateGenerator.setNotBefore(Date.from(LocalDate.now().plusDays(365).atStartOfDay().toInstant(ZoneOffset.UTC)));
        certificateGenerator.setNotAfter(new Date());
        certificateGenerator.setSignatureAlgorithm("SHA256WithRSAEncryption");

        //GENERATE CERTIFICATE SIGNING WITH THE SERVER'S PRIVATE KEY
        X509Certificate certificate = certificateGenerator.generate(privateKey);

        //NAME AND PASSWORD TO STORE THE CERTIFICATE
        String username = "server";
        char[] password = "admin".toCharArray();

        //CREATE KEYSTORE
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, password);//cargar el keystore
        ks.setCertificateEntry(username, certificate);//public
        ks.setKeyEntry(username, privateKey, password, new Certificate[]{certificate});//private, unlocked by the user's personal password
        FileOutputStream fos = new FileOutputStream("keystore.pfx");
        ks.store(fos, password);
        fos.close();
    }
}
