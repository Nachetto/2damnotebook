package jakarta.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UsuarioService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Path("/verify")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestVerify {
    private final UsuarioService usuarioService;

    @Inject
    public RestVerify(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    public Response verifyCode(@QueryParam("code") String code) {
        if (code == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            if (usuarioService.verifyCode(code)) {
                return Response.ok("Correo electrónico verificado correctamente").type(MediaType.TEXT_PLAIN).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}