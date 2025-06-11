package jakarta.rest;

import domain.modelo.Suscripcion;
import model.errores.BaseDatosCaidaException;
import model.errores.OtraException;
import service.SuscripcionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/suscripciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSuscripciones {

    private final SuscripcionService suscripcionService;

    @Inject
    public RestSuscripciones(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @GET
    public Response getAllSuscripciones() throws BaseDatosCaidaException {
        List<Suscripcion> suscripciones = suscripcionService.getAll();
        return Response.status(Response.Status.OK).entity(suscripciones).build();
    }

    @GET
    @Path("/usuario/{uuid}")
    public Response getAllSuscripcionesByUsuario(@PathParam("uuid") String uuid) throws BaseDatosCaidaException, OtraException {
        List<Suscripcion> suscripciones = suscripcionService.getAll(uuid);
        return Response.status(Response.Status.OK).entity(suscripciones).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getSuscripcion(@PathParam("uuid") String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        Suscripcion suscripcion = suscripcionService.get(uuid);
        return Response.status(Response.Status.OK).entity(suscripcion).build();
    }

    @POST
    public Response addSuscripcion(Suscripcion suscripcion) throws BaseDatosCaidaException, OtraException {
        Suscripcion nuevaSuscripcion = suscripcionService.save(suscripcion);
        return Response.status(Response.Status.CREATED).entity(nuevaSuscripcion).build();
    }

    @PUT
    @Path("/{uuid}")
    public Response updateSuscripcion(@PathParam("uuid") String uuid, Suscripcion suscripcionModificada) throws BaseDatosCaidaException, OtraException, NotFoundException {
        Suscripcion suscripcionOriginal = suscripcionService.get(uuid);
        Suscripcion suscripcionActualizada = suscripcionService.modify(suscripcionOriginal, suscripcionModificada);
        return Response.status(Response.Status.OK).entity(suscripcionActualizada).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response deleteSuscripcion(@PathParam("uuid") String uuid) throws OtraException, NotFoundException {
        boolean resultado = suscripcionService.delete(uuid);
        if (resultado) {
            return Response.status(Response.Status.OK).entity("Suscripción borrada con éxito").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al borrar la suscripción").build();
        }
    }
}
