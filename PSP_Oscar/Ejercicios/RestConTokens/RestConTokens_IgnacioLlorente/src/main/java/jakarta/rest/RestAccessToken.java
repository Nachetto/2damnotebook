package jakarta.rest;

import domain.modelo.JwtCredential;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.security.InMemoryIdentityStore;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UsuarioService;

import java.util.HashSet;
import java.util.Set;

@Path("/accesstoken")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestAccessToken {

    private final InMemoryIdentityStore identity;

    private final UsuarioService service;

    @Inject
    public RestAccessToken(InMemoryIdentityStore identity, UsuarioService service) {
        this.identity = identity;
        this.service = service;
    }

    @GET
    public Response getNuevoAccessToken(@QueryParam("refreshToken") String refreshToken) {
        boolean valido = false;
        String usuario = "";

        if (refreshToken != null) {
            String[] valores = refreshToken.split(" ");
            if (valores[0].equalsIgnoreCase("Bearer")) {//beep boop biip validando
                JwtCredential tokenBruto = new JwtCredential(valores[1]);
                CredentialValidationResult result = identity.validate(tokenBruto);

                if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                    valido = true;
                    usuario = result.getCallerPrincipal().getName();
                }
            }
        }

        if (!valido) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        //generar nuevo accessToken
        String nuevoAccessToken;
        if (service.getUsernameFromEmail(usuario).equalsIgnoreCase("oscarnovillo")){
            //una vez que se ha comprobado que el usuario es un administrador, se le da un token con los roles de administrador y usuario
            nuevoAccessToken = service.generateToken(usuario,"access","admin");
        }
        else{
            //una vez que se ha comprobado que el usuario es un usuario normal, se le da un token con los roles por defecto
            nuevoAccessToken = service.generateToken(usuario,"access");
        }

        return Response.ok(nuevoAccessToken).build();
    }
}
