package api;

import com.google.gson.Gson;
import model.users.base.User;
import model.users.management.Admin;
import storage.base.DAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@WebServlet("/api/admins/*")
public class AdminsServlet extends HttpServlet {
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

            List<Admin> admins = factory.getAdminDAO().read();
            for (Admin admin : admins) {
                if (admin.getLogin().equals(login)) {
                    if (admin.getPassword().equals(password)) {
                        session.setAttribute("role", 2);
                        resp.sendRedirect("admin/admin.html");
                        return;
                    }
                    resp.getWriter().write("{\"error\": \"Неверный пароль!\"}");
                    return;
                }
            }
        } else {
            resp.sendRedirect("admin/admin.html");
            return;
        }
        resp.getWriter().write("{\"error\": \"Администратора с таким логином не существует!\"}");
    }
}
