package playerServlets;

import engine.engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team.turn.ClueValidator;

import java.io.IOException;

@WebServlet(name = "play turn servlet", urlPatterns = "/play-turn")
public class PlayTurnServlet  extends HttpServlet {
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
        String roleString = req.getParameter("role");
        int gameIndex = Integer.parseInt(req.getParameter("game index"));
        int teamIndex = Integer.parseInt(req.getParameter("team index"));
        if(!engine.isGameActive(gameIndex)){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            res.getWriter().write("Game is still pending!");
        }
        else{
            if(engine.isTurnAllowedForTeam(teamIndex, gameIndex)){
                if(engine.isTurnAllowedForRole(roleString, gameIndex)){
                    res.getWriter().write("Your turn it is!");
                }
                else{
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                    res.getWriter().write("It's not your role's turn yet!");
                }
            }
            else{
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                res.getWriter().write("It's not your team's turn yet!");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if(req.getParameterMap().size() == 3){
            //definer turn
            int gameIndex = Integer.parseInt(req.getParameter("game index"));
            String clueWord = req.getParameter("clue word");
            int howManyWords = Integer.parseInt(req.getParameter("how many words"));

            boolean isClueWordValid;
            isClueWordValid = !ClueValidator.isClueWordOnBoard(clueWord, engine.getBoardState(gameIndex)) &&
                                !ClueValidator.isClueWordSubStringOfWordOnBoard(clueWord, engine.getBoardState(gameIndex));
            if(!isClueWordValid){
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                res.getWriter().write("Error: The clue word must not contain spaces, cannot match or be part of any word on the board.");
            }
            else{
                engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(true);
                engine.getAllGamesList().get(gameIndex).setCurrentClueWord(clueWord);
                engine.getAllGamesList().get(gameIndex).setCurrentNumOfGuesses(howManyWords);
                res.getWriter().write("Clue received!");
            }
        }
        else {
            //guesser turn
            //KEEP CODE FROM HERE!!!!
        }
    }
}
