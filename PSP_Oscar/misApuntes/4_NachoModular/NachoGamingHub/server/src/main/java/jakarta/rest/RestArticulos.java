package jakarta.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import domain.modelo.Articulo;
import model.errores.BaseDatosCaidaException;
import model.errores.OtraException;
import service.ArticuloService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/articulos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestArticulos {

    private final ArticuloService articuloService;

    @Inject
    public RestArticulos(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GET
    public Response getAllArticulos() throws BaseDatosCaidaException {
        List<Articulo> articulos = articuloService.getAll();
        return Response.status(Response.Status.OK).entity(articulos).build();
    }

    @GET
    @Path("/juego/{uuid}")
    public Response getAllArticulosByJuego(@PathParam("uuid") String uuid) throws BaseDatosCaidaException, OtraException {
        List<Articulo> articulos = articuloService.getAllByJuego(uuid);
        return Response.status(Response.Status.OK).entity(articulos).build();
    }

    @GET
    @Path("/usuario/{uuid}")
    public Response getAllArticulosByUsuario(@PathParam("uuid") String uuid) throws BaseDatosCaidaException, OtraException {
        List<Articulo> articulos = articuloService.getAllByUsuario(uuid);
        return Response.status(Response.Status.OK).entity(articulos).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getArticulo(@PathParam("uuid") String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        Articulo articulo = articuloService.get(uuid);
        return Response.status(Response.Status.OK).entity(articulo).build();
    }

    @POST
    public Response addArticulo(Articulo articulo) throws BaseDatosCaidaException, OtraException {
        Articulo nuevoArticulo = articuloService.save(articulo);
        return Response.status(Response.Status.CREATED).entity(nuevoArticulo).build();
    }

    @PUT
    @Path("/{uuid}")
    public Response updateArticulo(@PathParam("uuid") String uuid, Articulo articuloModificado) throws BaseDatosCaidaException, OtraException, NotFoundException {
        Articulo articuloOriginal = articuloService.get(uuid);
        Articulo articuloActualizado = articuloService.modify(articuloOriginal, articuloModificado);
        return Response.status(Response.Status.OK).entity(articuloActualizado).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response deleteArticulo(@PathParam("uuid") String uuid) throws OtraException, NotFoundException {
        boolean resultado = articuloService.delete(uuid);
        if (resultado) {
            return Response.status(Response.Status.OK).entity("Artículo borrado con éxito").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al borrar el artículo").build();
        }
    }
}

