package adminServlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import engine.engine.Engine;
import engine.game.ActiveGameInfo;
import engine.game.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "Live Games Display Servlet", urlPatterns = "/live-games")
public class LiveGamesServlet extends HttpServlet {
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
        if(req.getParameterMap().isEmpty()){
            //no params, display all active games
            List<ActiveGameInfo> activeGamesList = new ArrayList<>();
            Game currGame;
            for(int i=0;i<engine.getAllGamesList().size();i++){
                currGame = engine.getAllGamesList().get(i);
                if(currGame.isGameActive()){
                    activeGamesList.add(new ActiveGameInfo(i,currGame.getGameName(),currGame.getReadyTeamsAmount(),currGame.getTeams().size()));
                }
            }


            String jsonResponse = gson.toJson(activeGamesList);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonResponse);

        }
        else{
            //admin chose game to watch, display live game details

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            String liveGameIndexString = req.getParameter("Live game choice");
            int liveGameIndex = Integer.parseInt(liveGameIndexString);
            if(!engine.getAllGamesList().get(liveGameIndex).isGameActive()){
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                res.getWriter().write("Game Ended! It returned to Pending!");
            }
            else{
                String jsonResponse = gson.toJson(engine.getAllGamesList().get(liveGameIndex));
                res.getWriter().write(jsonResponse);
            }

        }
    }
}
