package dao.impl;

import dao.UsuarioDao;
import domain.modelo.Usuario;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class UsuarioDaoImpl implements UsuarioDao {
    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("usuario1", "contraseña1", false, LocalDateTime.now(), "codigo1"));
        usuarios.add(new Usuario("usuario2", "contraseña2", false, LocalDateTime.now(), "codigo2"));
        usuarios.add(new Usuario("usuario3", "contraseña3", false, LocalDateTime.now(), "codigo3"));
        usuarios.add(new Usuario("usuario4", "contraseña4", false, LocalDateTime.now(), "codigo4"));
        usuarios.add(new Usuario("usuario5", "contraseña5", false, LocalDateTime.now(), "codigo5"));
    }

    @Override
    public List<Usuario> getAll() {
        return usuarios;
    }

    @Override
    public Usuario get(String username) {
        return usuarios.stream().filter(usuario -> usuario.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public boolean save(Usuario o) {
        return usuarios.add(o);
    }

    public String generateActivationCode() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    public boolean verifyCode(String c) {
        //buscar el usuario con el codigo de activacion y activarlo
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getActivationCode().equals(c))
                .findFirst().orElse(null);

        //30 segundos de caducidad
        if (u != null && !u.getLastLifeSignal().plusSeconds(30).isBefore(LocalDateTime.now())) {
            u.setActivated(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean modify(Usuario o, Usuario o2) {
        return true;
    }

    @Override
    public boolean delete(String username) {
        return false;
    }

    public boolean login(String email, String passwordHasheada) {
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getUsername().equals(email))
                .findFirst().orElse(null);
        return (u != null && u.getPassword().equals(passwordHasheada));
    }

    public boolean isActivated(String email) {
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getUsername().equals(email))
                .findFirst().orElse(null);
        return (u != null && u.isActivated());
    }

    //esto creo que va aqui porque el token es para los usuarios no voy a hacer un dao para el token porque no tiene sentido
    public String generateToken(String tipo, String username) {
        try {
            Date date;
            if (tipo.equalsIgnoreCase("access"))
                date= Date.from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault())
                        .toInstant());
            else if (tipo.equalsIgnoreCase("refresh"))
                date= Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault())
                        .toInstant());
            else
                return null;

            SecretKey keyConfig = getSecretKey();

            //supongo que este token varía en funcion del tipo de usuario pero para esta practica lo dejo asi
            return Jwts.builder()
                    .setSubject("RestConTokens")
                    .setIssuer("IgnacioLlorente")//esto deberia de ser el nombre de la aplicacion
                    .setExpiration(date)
                    .claim("user", username)
                    .claim("group", "users")
                    .claim("tipo", tipo)//o accessToken o refreshToken
                    .signWith(keyConfig).compact();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String generateToken(String tipo, String username, String extraRoles){//para usuarios especiales como administradores
        Date date;//se podria poner un tiempo de expiracion mas largo para los administradores pero ya ves tu
        if (tipo.equalsIgnoreCase("access"))
            date= Date.from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault())
                    .toInstant());
        else if (tipo.equalsIgnoreCase("refresh"))
            date= Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault())
                    .toInstant());
        else
            return null;
        try {
            SecretKey keyConfig = getSecretKey();
            //supongo que este token varía en funcion del tipo de usuario pero para esta practica lo dejo asi
            return Jwts.builder()
                    .setSubject("RestConTokens")
                    .setIssuer("IgnacioLlorente")//esto deberia de ser el nombre de la aplicacion
                    .setExpiration(date)
                    .claim("user", username)
                    .claim("group", "users,"+extraRoles)
                    .claim("tipo", tipo)//o accessToken o refreshToken
                    .signWith(keyConfig).compact();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    //igual esto habria que hacerlo privado pero lo uso cuando valido el token en el servlet
    public SecretKey getSecretKey() throws NoSuchAlgorithmException {
        final MessageDigest digest =
                MessageDigest.getInstance("SHA-512");
        digest.update("clave".getBytes(StandardCharsets.UTF_8));
        final SecretKeySpec key2 = new SecretKeySpec(
                digest.digest(), 0, 64, "AES");
        SecretKey keyConfig = Keys.hmacShaKeyFor(key2.getEncoded());
        return keyConfig;
    }


    public String getUsernameFromEmail(String email) {
        //sacar el usuario del gmail limpio
        String[] emailSplit = email.split("@");
        if (emailSplit[1].equalsIgnoreCase("gmail.com") && emailSplit[0].contains("+")) {
            emailSplit[0] = emailSplit[0].split("\\+")[0];
        }
        if (emailSplit[1].equalsIgnoreCase("gmail.com") && emailSplit[0].contains("."))
            emailSplit[0] = emailSplit[0].replace(".", "");
        return emailSplit[0];
    }
}
