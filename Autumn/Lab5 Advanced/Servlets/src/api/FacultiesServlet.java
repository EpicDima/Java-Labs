package api;

import com.google.gson.Gson;
import model.Faculty;
import storage.base.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/faculties/*")
public class FacultiesServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final DAOFactory factory = DAOFactory.create(DAOFactory.Type.MYSQL);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        List<Faculty> faculties = factory.getFacultyDAO().getActiveFaculties();

        resp.getWriter().print(gson.toJson(faculties));
    }
}
