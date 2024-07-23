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
        String teamIndexString = req.getParameter("team index");
        int gameIndex = Integer.parseInt(gameIndexString);
        int teamIndex = Integer.parseInt(teamIndexString);
        if(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().contains(teamIndex)) {

            if (engine.getGameTeamsByGameIndex(gameIndex).get(teamIndex).isWon()) {
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                res.getWriter().write("Your Team Won!");
            } else {
                if (engine.getGameTeamsByGameIndex(gameIndex).get(teamIndex).isLost()) {
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                    res.getWriter().write("Your team lost because black card got picked!");
                }

            }
            decrementUsersAndCheckToResetGame(gameIndex);
        }
        else{
            if(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().size() == engine.getGameTeamsByGameIndex(gameIndex).size() - 1){
                res.setStatus(HttpServletResponse.SC_CONFLICT);

                if(engine.getGameTeamsByGameIndex(gameIndex)
                        .get(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().get(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().size() - 1))
                        .isWon()){
                    res.getWriter().write("The last other team won! You lose!");
                }
                else{
                    res.getWriter().write("The last other team lost! You won!");
                }
                decrementUsersAndCheckToResetGame(gameIndex);
            }
            else{
                String jsonResponse = gson.toJson(engine.getAllGamesList().get(gameIndex));
                res.getWriter().write(jsonResponse);
            }
        }




    }

    private void decrementUsersAndCheckToResetGame(int gameIndex){
        engine.getAllGamesList().get(gameIndex).decementUserCounter();
        if(engine.getAllGamesList().get(gameIndex).getUsersCounter() == 0){
            engine.resetGame(gameIndex);
        }
    }

}
