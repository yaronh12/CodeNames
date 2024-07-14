package playerServlets;

import com.google.gson.Gson;
import engine.engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "game status servlet", urlPatterns = "/live-game-status")
public class GameStatusServlet extends HttpServlet {
    private Engine engine;
    @Override
    public void init() throws ServletException {
        engine = (Engine)getServletContext().getAttribute("myEngine");
        if(engine == null){
            throw new ServletException("Engine not initiated in servlet context!");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson gson = new Gson();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String gameIndexString = req.getParameter("Live game status index");
        int gameIndex = Integer.parseInt(gameIndexString);
        String jsonResponse = gson.toJson(engine.getAllGamesList().get(gameIndex));
        res.getWriter().write(jsonResponse);

    }

}
