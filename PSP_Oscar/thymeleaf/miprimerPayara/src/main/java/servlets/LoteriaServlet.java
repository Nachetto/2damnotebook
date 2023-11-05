package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "LoteriaServlet", value = "/loteria")
public class LoteriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Inicializar el juego en la primera visita
        if (req.getAttribute("numberToGuess") == null) {
            int numberToGuess = new Random().nextInt(10) + 1;
            req.setAttribute("numberToGuess", numberToGuess);
            req.setAttribute("attempts", 5);
        }

        req.getRequestDispatcher("/loteria.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int numberToGuess = (int) req.getAttribute("numberToGuess");
        int attempts = (int) req.getAttribute("attempts");

        int userGuess = Integer.parseInt(req.getParameter("number"));

        if (userGuess == numberToGuess) {
            req.setAttribute("message", "¡Felicidades! Adivinaste el número.");
            req.removeAttribute("numberToGuess"); // Reiniciar el juego
            req.removeAttribute("attempts");
        } else {
            attempts--;
            if (attempts > 0) {
                if (userGuess < numberToGuess) {
                    req.setAttribute("message", "El número es mayor.");
                } else {
                    req.setAttribute("message", "El número es menor.");
                }
            } else {
                req.setAttribute("message", "¡Perdiste! El número era " + numberToGuess);
                req.removeAttribute("numberToGuess"); // Reiniciar el juego
                req.removeAttribute("attempts");
            }
        }

        req.getRequestDispatcher("/loteria.html").forward(req, resp);
    }
}
