package jakarta.rest;

import domain.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.rest.errores.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.errores.BaseDatosCaidaException;
import model.errores.OtraException;
import service.UsuarioService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
//    private final UsuarioService usuarioService;
//
//    @Inject
//    public RestLogin(UsuarioService usuarioService) {
//        this.usuarioService = usuarioService;
//    }+

    @GET
    public Response getLogin(@QueryParam("user") String user, @QueryParam("password") String password) {
        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Path("/test")
    @GET
    public Response test() {
        try {
            return Response.status(200).entity(new ApiError("Test", LocalDateTime.now())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
