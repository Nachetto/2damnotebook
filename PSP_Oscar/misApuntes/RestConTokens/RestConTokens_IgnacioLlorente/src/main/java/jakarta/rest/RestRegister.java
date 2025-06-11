package jakarta.rest;

import dao.impl.MandarMail;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UsuarioService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegister {
    private final UsuarioService usuarioService;

    @Inject
    public RestRegister(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    public Response doRegister(@QueryParam("email") String email, @QueryParam("password") String password) {
        if (email == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            String usuario = usuarioService.getUsernameFromEmail(email);

            String code = usuarioService.generateActivationCode();
            String encodedToken = URLEncoder.encode(code, StandardCharsets.UTF_8);//si no no funciona porque contendría caracteres invalidos

            MandarMail emailSender = new MandarMail();
            emailSender.generateAndSendEmail(email, "Sigue este enlace para activar tu cuenta: " +
                    "http://localhost:8080/RestConTokens-IgnacioLlorente/api/verify?code="
                    +encodedToken, usuario+", activa tu cuenta (Ignacio Llorente).");
            //EL ENLACE VARIA EN FUNCION DE LA IP PRIVADA DEL SERVIDOR, NO PUEDE SER LOCALHOST
            //PORQUE EN EL MOVIL NO FUNCIONARIA TODO: PONER LA IP DEL PAJARA DEL INSTITUTO

            //subir a base de datos el registro
            if (!usuarioService.save(email, password, code))
                return Response.status(Response.Status.BAD_REQUEST).build();
            else
                return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
