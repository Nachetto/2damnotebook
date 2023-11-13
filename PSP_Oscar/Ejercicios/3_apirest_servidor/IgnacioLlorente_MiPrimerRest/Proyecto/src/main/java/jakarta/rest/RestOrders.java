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
        if (either.isLeft()) {
            throw new NotFoundException(either.getLeft());
        } else {
            return Response.status(Response.Status.OK).entity(either.get()).build();
        }
    }


    @GET
    @Path(Constantes.ORDERIDPARAM)
    public Response getOrder(@PathParam(Constantes.ORDERID) String id) {
        try {
            int orderId = Integer.parseInt(id);
            Either<String, Order> either = service.get(orderId);
            if (either.isLeft()) {
                throw new NotFoundException(either.getLeft());
            } else {
                return Response.status(Response.Status.OK).entity(either.get()).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.NOTANINT).build();
        }
    }


    @POST
    public Response addOrder(Order order) {
        try {
            int result = service.save(order);
            if (result == 1) {
                return Response.status(Response.Status.CREATED).entity(Constantes.ORDERADDED+order).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ERRORADDINGORDER).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @PUT
    public Response updateOrder(Order order, @QueryParam(Constantes.ORDERID) String id) {
        try {
            int orderId = Integer.parseInt(id);
            Either<String, Order> getResult = service.get(orderId);
            if (getResult.isLeft()) {
                return Response.status(Response.Status.NOT_FOUND).entity(getResult.getLeft()).build();
            }

            int result = service.modify(order, getResult.get());
            if (result == 1) {
                return Response.status(Response.Status.OK).entity(order).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ORDERNOTFOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.NOTANINT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Path(Constantes.ORDERIDPARAM)
    public Response delOrder(@PathParam(Constantes.ORDERID) String id) {
        try {
            int orderId = Integer.parseInt(id);
            Either<String, Order> getResult = service.get(orderId);
            if (getResult.isLeft()) {
                return Response.status(Response.Status.NOT_FOUND).entity(getResult.getLeft()).build();
            }

            int result = service.delete(getResult.get());
            if (result == 1) {
                return Response.status(Response.Status.OK).entity(Constantes.BORRADO).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.ORDERNOTFOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constantes.NOTANINT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }



}

