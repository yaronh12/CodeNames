package playerServlets;

import engine.engine.Engine;
import engine.game.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team.team.Team;

import java.io.IOException;

@WebServlet(name = "game registry servlet", urlPatterns = "/register-to-game")

public class GameRegistryServlet  extends HttpServlet {
    private Engine engine;
    @Override
    public void init() throws ServletException {
        engine = (Engine)getServletContext().getAttribute("myEngine");
        if(engine == null){
            throw new ServletException("Engine not initiated in servlet context!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String gameIndexString = req.getParameter("game index");
        String teamIndexString = req.getParameter("team index");
        String roleString = req.getParameter("role");

        engine.registerPlayerToGame(Integer.parseInt(gameIndexString),Integer.parseInt(teamIndexString),roleString);
        engine.getAllGamesList().get(Integer.parseInt(gameIndexString)).incementUserCounter();
        res.getWriter().write("Registry Succeed!");


    }
}
