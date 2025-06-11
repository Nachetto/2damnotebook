package jakarta.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import service.CustomerService;

import java.util.List;

@Path(Constantes.CUSTOMERSPARAM)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCustomers {

    private final CustomerService su;

    @Inject
    public RestCustomers(CustomerService su) {
        this.su = su;
    }

    @GET
    public List<Customer> getAllCustomer() {
        Either<String, List<Customer>> either = su.getAll();
        return either.get();
    }

    @GET
    @Path(Constantes.IDPARAM)
    public Response getCustomer(@PathParam(Constantes.ID) String id) {
        int customerId = Integer.parseInt(id);
        Either<String, Customer> either = su.get(customerId);
        return Response.status(Response.Status.OK).entity(either.get()).build();
    }


    @GET
    @Path(Constantes.ID_HASANYORDERSPARAM)
    public Response hasanyorders(@PathParam(Constantes.ID) String id) {
        int customerId = Integer.parseInt(id);
        if (su.hasAnyOrders(customerId)) {
            return Response.status(Response.Status.OK).entity(Constantes.HAS_ORDERS).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Constantes.NO_ORDERS).build();
        }
    }


    @POST
    public Response addCustomer(Customer usuario) {
        int result = su.save(usuario);
        if (result == 1) {
            return Response.status(Response.Status.CREATED).entity(Constantes.USERADDED + usuario).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.USERNOTFOUND).build();
        }
    }


    @PUT
    public Response updateCustomer(Customer usuario, @QueryParam(Constantes.ID) String id) {
        int customerId = Integer.parseInt(id);
        Either<String, Customer> getResult = su.get(customerId);
        int result = su.modify(usuario, getResult.get());
        if (result == 1) {
            return Response.status(Response.Status.OK).entity(usuario).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.USERNOTFOUND).build();
        }
    }


    @DELETE
    @Path(Constantes.IDPARAM)
    public Response delCustomer(@PathParam(Constantes.ID) String id) {
        int customerId = Integer.parseInt(id);
        Either<String, Customer> getResult = su.get(customerId);
        int result = su.delete(getResult.get());
        if (result == 1) {
            return Response.status(Response.Status.OK).entity(Constantes.BORRADO).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.USERNOTFOUND).build();
        }
    }


}
