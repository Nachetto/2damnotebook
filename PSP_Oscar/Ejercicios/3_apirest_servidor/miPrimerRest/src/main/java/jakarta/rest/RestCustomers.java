package jakarta.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import service.CustomerService;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCustomers {

    private final CustomerService su;

    @Inject
    public RestCustomers(CustomerService su) {
        this.su = su;
    }

    @GET
    public List<Customer> getAllUsuario() {
        Either<String, List<Customer>> either = su.getAll();
        if (either.isLeft()) {
            throw new WebApplicationException(either.getLeft(), Response.Status.NOT_FOUND);
        }
        else {
            return either.get();
        }
    }

    @GET
    @Path("/{id}")
    public Customer getUsuario(@PathParam(Constantes.ID) String id) {
        Either<String, Customer> either = su.get(Integer.parseInt(id));
        if (either.isLeft()) {
            throw new WebApplicationException(either.getLeft(), Response.Status.NOT_FOUND);
        }
        else {
            return either.get();
        }
    }

    @GET
    @Path("/{id}/hasanyorders")
    public String hasanyorders(@PathParam(Constantes.ID) String id) {
        if(su.hasAnyOrders(Integer.parseInt(id)))
            return Constantes.HAS_ORDERS;
        else
            return Constantes.NO_ORDERS;
    }

    @POST
    public Response addUsuario(Customer usuario) {
        if(su.save(usuario)==1)
            return Response.status(Response.Status.CREATED)
                    .entity(usuario).build();
        else
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(usuario).build();
    }

    @PUT
    public Customer updateUsuario(Customer usuario, @QueryParam(Constantes.ID) String id) {
        if(su.modify(usuario, su.get(Integer.parseInt(id)).get())==1)
            return su.get(Integer.parseInt(id)).get();
        else
            return null;
    }

    @DELETE
    @Path("/{id}")
    public Response delUsuario(@PathParam(Constantes.ID) String id) {
        if (su.delete(su.get(Integer.parseInt(id)).get()) == 1)
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }


}
