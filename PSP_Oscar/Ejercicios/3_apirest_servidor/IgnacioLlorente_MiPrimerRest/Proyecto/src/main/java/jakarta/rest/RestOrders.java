package jakarta.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Order;
import service.OrderService;

import java.util.List;


@Path(Constantes.ORDERSPARAM)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestOrders {

    private final OrderService service;

    @Inject
    public RestOrders(OrderService service) {
        this.service = service;
    }

    @GET
    public Response getAllOrders() {
        Either<String, List<Order>> either = service.getAll();
        return Response.status(Response.Status.OK).entity(either.get()).build();
    }


    @GET
    @Path(Constantes.ORDERIDPARAM)
    public Response getOrder(@PathParam(Constantes.ORDERID) String id) {
        int orderId = Integer.parseInt(id);
        Either<String, Order> either = service.get(orderId);
        return Response.status(Response.Status.OK).entity(either.get()).build();
    }

    @GET
    @Path(Constantes.GETLASTORDERIDPARAM)
    public Response getLastOrderId() {
        int orderId = service.getLastOrderId();
        return Response.status(Response.Status.OK).entity(Constantes.EL_NUMERO +orderId).build();
    }


    @POST
    public Response addOrder(Order order) {
        int result = service.save(order);
        if (result == 1) {
            return Response.status(Response.Status.CREATED).entity(Constantes.ORDERADDED + order).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ERRORADDINGORDER).build();
        }
    }


    @PUT
    public Response updateOrder(Order order, @QueryParam(Constantes.ORDERID) String id) {
        int orderId = Integer.parseInt(id);
        Either<String, Order> getResult = service.get(orderId);
        int result = service.modify(order, getResult.get());
        if (result == 1) {
            return Response.status(Response.Status.OK).entity(order).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ORDERNOTFOUND).build();
        }
    }


    @DELETE
    @Path(Constantes.ORDERIDPARAM)
    public Response delOrder(@PathParam(Constantes.ORDERID) String id) {
        int orderId = Integer.parseInt(id);
        Either<String, Order> getResult = service.get(orderId);
        int result = service.delete(getResult.get());
        if (result == 1) {
            return Response.status(Response.Status.OK).entity(Constantes.BORRADO).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ORDERNOTFOUND).build();
        }
    }


}

