package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet( name = "Servlet", value = "/Servlet")
public class MiPrimerServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            resp.getWriter().println("""
                    <html>
                        <head>
                            <title>Mi primer servlet</title>
                        </head>
                        <body>
                            <h1>Mi primer servlet</h1>
                            <p>Este es mi primer servlet</p>
                        </body>
                    </html>
                    
                    """
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
