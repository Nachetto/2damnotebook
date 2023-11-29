package jakarta.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import domain.modelo.Juego;
import service.JuegoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/juegos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJuegos {

    private final JuegoService juegoService;

    @Inject
    public RestJuegos(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @GET
    public Response getAllJuegos() {
        List<Juego> juegos = juegoService.getAll();
        return Response.status(Response.Status.OK).entity(juegos).build();
    }

    @GET
    @Path("/{id}")
    public Response getJuego(@PathParam("id") String id) {
        Juego juego = juegoService.get(id);
        return Response.status(Response.Status.OK).entity(juego).build();
    }

    @POST
    public Response addJuego(Juego juego) {
        Juego nuevoJuego = juegoService.save(juego);
        return Response.status(Response.Status.CREATED).entity(nuevoJuego).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateJuego(@PathParam("id") String id, Juego juegoModificado) {
        Juego juegoOriginal = juegoService.get(id);
        Juego juegoActualizado = juegoService.modify(juegoOriginal, juegoModificado);
        return Response.status(Response.Status.OK).entity(juegoActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteJuego(@PathParam("id") String id) {
        UUID uuid = UUID.fromString(id);
        boolean resultado = juegoService.delete(uuid);
        if (resultado) {
            return Response.status(Response.Status.OK).entity("Juego borrado con éxito").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al borrar el juego").build();
        }
    }
}
