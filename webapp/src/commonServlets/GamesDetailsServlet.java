package commonServlets;

import com.google.gson.Gson;
import engine.engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Games Details Display Servlet", urlPatterns = "/games-details")
public class GamesDetailsServlet extends HttpServlet {
    private Engine engine;
    @Override
    public void init() throws ServletException {
        engine = (Engine)getServletContext().getAttribute("myEngine");
        if(engine == null){
            throw new ServletException("Engine not initiated in servlet context!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(engine.getAllGamesList());
        try {
            res.getWriter().write(jsonResponse);
           // res.getWriter().write(engine.getGamesDetails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
