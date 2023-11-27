package jakarta.filtros;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {"/sdawdasd"})
public class AuthFiltro implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("LOGIN") == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No estás autorizado. Por favor, inicia sesión.");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

