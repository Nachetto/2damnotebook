package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "LoteriaServlet", value = "/loteria")
public class LoteriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Integer attempts = (Integer) session.getAttribute("attempts");
        Integer numberToGuess = (Integer) session.getAttribute("numberToGuess");

        if (numberToGuess == null) {
            numberToGuess = new Random().nextInt(10) + 1;
            session.setAttribute("numberToGuess", numberToGuess);
        }

        if (attempts == null) {
            attempts = 5;
            session.setAttribute("attempts", attempts);
        }

        req.getRequestDispatcher("/WEB-INF/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Integer attempts = (Integer) session.getAttribute("attempts");
        Integer numberToGuess = (Integer) session.getAttribute("numberToGuess");

        String userGuessParam = req.getParameter("number");

        if (userGuessParam != null && !userGuessParam.isEmpty()) {
            try {
                int userGuess = Integer.parseInt(userGuessParam);

                if (userGuess == numberToGuess) {
                    req.setAttribute("message", "¡Felicidades! Adivinaste el número.");
                    session.removeAttribute("numberToGuess");
                    session.removeAttribute("attempts");
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
                        session.removeAttribute("numberToGuess");
                        session.removeAttribute("attempts");
                    }
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Por favor, ingresa un número válido.");
            }
        } else {
            req.setAttribute("message", "Por favor, ingresa un número válido.");
        }

        req.getRequestDispatcher("/WEB-INF/index.html").forward(req, resp);
    }
}
