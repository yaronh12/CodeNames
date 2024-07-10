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
        res.setContentType("text/plain");
        try {
            res.getWriter().write(engine.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
