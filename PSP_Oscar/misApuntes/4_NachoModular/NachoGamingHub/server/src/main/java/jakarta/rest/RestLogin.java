package jakarta.rest;

import domain.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.OtraException;
import service.UsuarioService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {

    private final UsuarioService usuarioService;

    @Inject
    public RestLogin(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
    }

    @Context
    private HttpServletRequest request;

    @GET
    public Response getLogin(@QueryParam("user") String user, @QueryParam("password") String password) {
        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            boolean isAuthenticated = usuarioService.authenticate(user, password);
            if (isAuthenticated) {
                request.getSession().setAttribute("LOGIN", true);
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{Name}")
    public Response getUsuarioFromName(@PathParam("Name") String name) throws BaseDatosCaidaException, NotFoundException, OtraException {
        Usuario usuario = usuarioService.getFromName(name);
        return Response.status(Response.Status.OK).entity(usuario).build();
    }
}
