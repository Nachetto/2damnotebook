package jakarta.security;

import domain.modelo.JwtCredential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.HashSet;
import java.util.Set;

//filtro global en todas las peticiones que extrae las cabeceras, comprueba que el token es valido y si lo es mete a
//el usuario a la peticion y magia borras todos felices fiesta con fuegos artificiales y helados estoy desvariando ayuda
@ApplicationScoped
public class TokenVerifier implements HttpAuthenticationMechanism {

    private final InMemoryIdentityStore identity;

    @Inject
    public TokenVerifier(InMemoryIdentityStore identity) {
        this.identity = identity;
    }


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        String requestURI = httpServletRequest.getRequestURI();

        // para login no mires la cabecera
        if (requestURI.equals("/login")||requestURI.equals("/registro")||requestURI.equals("/verify")||requestURI.equals("/accesstoken")) {
            return httpMessageContext.doNothing();
        }

        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] valores = header.split(" ");
            if (valores[0].equalsIgnoreCase("Bearer")) {
                //coges los datos del token y miras el usuario, los roles que tiene y los metes en el security context
                c = identity.validate(new JwtCredential(valores[1]));
                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    //dar los usuarios y roles al security context
                    Set<String> roles = new HashSet<>(); // Retrieve roles from your user management system
                    return httpMessageContext.notifyContainerAboutLogin(c.getCallerPrincipal().getName(), roles);
                }
            }
        }

        if (!c.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            httpServletRequest.setAttribute("status", c.getStatus());
            return httpMessageContext.doNothing(); //esto es lo que hace que no se pueda acceder a los recursos
        }

        return httpMessageContext.doNothing();
    }
}
