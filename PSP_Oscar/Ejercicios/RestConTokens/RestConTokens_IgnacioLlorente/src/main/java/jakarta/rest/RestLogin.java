package jakarta.rest;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UsuarioService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    private final UsuarioService service;

    @Inject
    public RestLogin(UsuarioService service) {
        this.service = service;
    }

    @GET
    public Response getLogin(@QueryParam("email") String email, @QueryParam("password") String password) {
        if (email == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            //comprobar si la contraseña es correcta
            if (service.login(email, password)) {
                String accessToken;
                String refreshToken;
                //aqui se podria gestionar una lista de admisnitadores con su correo electronico pasando un filtro del dominio del correo, pero para simplificarlo se ha hecho asi
                if (service.getUsernameFromEmail(email).equalsIgnoreCase("oscarnovillo")){//doxeado jeje
                    //una vez que se ha comprobado que el usuario es un administrador, se le da un token con los roles de administrador y usuario
                    accessToken = service.generateToken(email,"access","admin");
                    refreshToken = service.generateToken(email,"refresh","admin");
                }
                else{
                    //una vez que se ha comprobado que el usuario es un usuario normal, se le da un token con los roles de usuario
                    accessToken = service.generateToken(email,"access");
                    refreshToken = service.generateToken(email,"refresh");
                }


                JsonObject tokens = Json.createObjectBuilder()
                        .add("accessToken", accessToken)
                        .add("refreshToken", refreshToken)
                        .build();

                return Response.ok(tokens).build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}