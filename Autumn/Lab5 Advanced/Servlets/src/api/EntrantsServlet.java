package api;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;
import model.users.base.User;
import model.users.management.Admin;
import model.users.study.Entrant;
import storage.base.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@WebServlet("/api/entrants/*")
public class EntrantsServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final DAOFactory factory = DAOFactory.create(DAOFactory.Type.MYSQL);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        HttpSession session = req.getSession();
        Integer role = (Integer) session.getAttribute("role");
        if (role == null) {
            String login = req.getHeader("login");
            String password = req.getHeader("password");

            List<Entrant> entrants = factory.getEntrantDAO().read();
            for (Entrant entrant : entrants) {
                if (entrant.getLogin().equals(login)) {
                    if (entrant.getPassword().equals(password)) {
                        session.setAttribute("role", 1);
                        resp.sendRedirect("entrant/entrant.html");
                        return;
                    }
                    resp.getWriter().write("{\"error\": \"Неверный пароль!\"}");
                    return;
                }
            }
        } else {
            resp.sendRedirect("entrant/entrant.html");
            return;
        }
        resp.getWriter().write("{\"error\": \"Абитуриента с таким логином не существует!\"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        HttpSession session = req.getSession();
        Integer role = (Integer) session.getAttribute("role");
        if (role == null) {
            Entrant entrant = gson.fromJson(req.getReader(), Entrant.class);
            entrant.setEnrolled(Entrant.Enrolled.UNKNOWN);
            if (factory.getEntrantDAO().create(entrant)) {
                session.setAttribute("role", 1);
                resp.sendRedirect("entrant/entrant.html");
            } else {
                resp.getWriter().write("{\"error\": \"Абитуриент с таким логином уже существует!\"}");
            }
        } else {
            resp.sendRedirect("authorization.html");
        }
    }
}
