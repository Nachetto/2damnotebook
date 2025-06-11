package jakarta.filtros;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.errores.OtraException;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {"/api/", "/api/juegos","/api/articulos","/api/usuarios","/api/suscripciones"})
public class AuthFiltro implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("LOGIN") == null) {
            throw new OtraException("No estás autorizado. Por favor, inicia sesión.");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

