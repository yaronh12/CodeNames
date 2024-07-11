package playerServlets;

import com.google.gson.Gson;
import engine.engine.Engine;
import engine.game.ActiveGameInfo;
import engine.game.Game;
import engine.game.PendingGameInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Pending Games Display Servlet", urlPatterns = "/pending-games")
public class PendingGamesServlet extends HttpServlet {
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
        List<PendingGameInfo> pendingGamesList = new ArrayList<>();
        Game currGame;
        for(int i=0;i<engine.getAllGamesList().size();i++){
            currGame = engine.getAllGamesList().get(i);
            if(!currGame.isGameActive()){
                pendingGamesList.add(new PendingGameInfo(i,currGame));
            }
        }

        String jsonResponse = gson.toJson(pendingGamesList);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(jsonResponse);
    }
}
