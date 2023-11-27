package jakarta.rest;
import domain.modelo.Usuario;
import model.errores.BaseDatosCaidaException;
import model.errores.OtraException;
import service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private final UsuarioService usuarioService;

    @Inject
    public RestUsuarios(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    public List<Usuario> getAllUsuarios() throws BaseDatosCaidaException, NotFoundException {
        return usuarioService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getUsuario(@PathParam("id") String id) throws BaseDatosCaidaException, NotFoundException, OtraException {
        Usuario usuario = usuarioService.get(id);
        return Response.status(Response.Status.OK).entity(usuario).build();
    }

    @POST
    public Response addUsuario(Usuario usuario) throws BaseDatosCaidaException, OtraException {
        Usuario result = usuarioService.save(usuario);
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUsuario(@PathParam("id") String id, Usuario modifiedUsuario) throws BaseDatosCaidaException, OtraException, NotFoundException {
        UUID uuid = UUID.fromString(id);
        Usuario initialUsuario = usuarioService.get(id);
        Usuario result = usuarioService.modify(initialUsuario, modifiedUsuario);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUsuario(@PathParam("id") String id) throws OtraException, NotFoundException {
        UUID uuid = UUID.fromString(id);
        boolean result = usuarioService.delete(uuid);
        if (result) {
            return Response.status(Response.Status.OK).entity("Usuario borrado con éxito").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al borrar usuario").build();
        }
    }
}
