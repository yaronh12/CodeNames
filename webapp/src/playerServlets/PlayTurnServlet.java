package playerServlets;

import com.google.gson.Gson;
import engine.engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team.turn.ClueValidator;
import team.turn.Guess;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
                if(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().contains(teamIndex)){
                    if(engine.getGameTeamsByGameIndex(gameIndex).get(teamIndex).isWon()){
                        res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                        res.getWriter().write("Your Team Won!");
                        decrementUsersAndCheckToResetGame(gameIndex);
                    }
                    else{
                        if(engine.getGameTeamsByGameIndex(gameIndex).get(teamIndex).isLost()){
                            res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                            res.getWriter().write("Your team lost because black card got picked!");
                            decrementUsersAndCheckToResetGame(gameIndex);

                        }

                    }
                }
                else {

                    if(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().size() == engine.getGameTeamsByGameIndex(gameIndex).size() - 1){
                        res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                        if(engine.getGameTeamsByGameIndex(gameIndex)
                                .get(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().get(engine.getTeamsInfo(gameIndex).getFinishedTeamsIndices().size() - 1))
                                .isWon()){
                            res.getWriter().write("The last other team won! You lose!");
                            decrementUsersAndCheckToResetGame(gameIndex);

                        }
                        else{
                            res.getWriter().write("The last other team lost! You won!");
                            decrementUsersAndCheckToResetGame(gameIndex);

                        }
                    }
                    else {
                        if (engine.isTurnAllowedForTeam(teamIndex, gameIndex)) {
                            if (engine.isTurnAllowedForRole(roleString, gameIndex)) {
                                Gson gson = new Gson();
                                res.setContentType("application/json");
                                res.setCharacterEncoding("UTF-8");
                                String jsonResponse = gson.toJson(engine.getAllGamesList().get(gameIndex));
                                res.getWriter().write(jsonResponse);
                            } else {
                                res.setStatus(HttpServletResponse.SC_CONFLICT);
                                res.getWriter().write("It's not your role's turn yet!");
                            }
                        } else {
                            res.setStatus(HttpServletResponse.SC_CONFLICT);
                            res.getWriter().write("It's not your team's turn yet!");
                        }
                    }
                }


        }

    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int gameIndex = Integer.parseInt(req.getParameter("game index"));
        if(req.getParameterMap().size() == 3){
            //definer turn
            if(!engine.getAllGamesList().get(gameIndex).isDefinitionAlreadyTaken()) {
                String clueWord = req.getParameter("clue word");
                int howManyWords = Integer.parseInt(req.getParameter("how many words"));

                boolean isClueWordValid;
                isClueWordValid = !ClueValidator.isClueWordOnBoard(clueWord, engine.getBoardState(gameIndex)) &&
                        !ClueValidator.isClueWordSubStringOfWordOnBoard(clueWord, engine.getBoardState(gameIndex));
                if (!isClueWordValid) {
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                    res.getWriter().write("Error: The clue word must not contain spaces, cannot match or be part of any word on the board.");
                } else {
                    engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(true);
                    engine.getAllGamesList().get(gameIndex).setCurrentClueWord(clueWord);
                    engine.getAllGamesList().get(gameIndex).setCurrentNumOfGuesses(howManyWords);
                    res.getWriter().write("Clue received!");
                }
            }
            else{
                //res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                res.getWriter().write("You're late! other definer already gave the clue!");
            }
        }
        else {
            //guesser turn
            //KEEP CODE FROM HERE!!!!

            int guessIndex = Integer.parseInt(req.getParameter("guess index"));
            if(engine.getAllGamesList().get(gameIndex).isDefinitionAlreadyTaken()) {
                Guess guess = engine.makeGuess(guessIndex - 1, gameIndex);
                String guessResponse = giveGuessResponse(guess);
                if (guessResponse.equals("Correct!")) {

                    engine.getAllGamesList().get(gameIndex).setCurrentNumOfGuesses(engine.getAllGamesList().get(gameIndex).getCurrentNumOfGuesses() - 1);
                    if (engine.isGameOver(guess, gameIndex)) {
                        guessResponse += "\nCongrats! You finished all your words!";
                        res.setStatus(HttpServletResponse.SC_CONFLICT);
                        //engine.passTurn(gameIndex);
                        engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(false);
                        decrementUsersAndCheckToResetGame(gameIndex);

                    }
                    if (engine.getAllGamesList().get(gameIndex).getCurrentNumOfGuesses() == 0) {
                        engine.passTurn(gameIndex);
                        engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(false);
                    }
                    res.getWriter().write(guessResponse);
                }
                else {

                    if (engine.isGameOver(guess, gameIndex)) {
                        res.setStatus(HttpServletResponse.SC_CONFLICT);
                        decrementUsersAndCheckToResetGame(gameIndex);
                        engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(false);
                    } else {
                        res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                        if(guess.isNeedToPassTurn()){
                            engine.passTurn(gameIndex);
                            engine.getAllGamesList().get(gameIndex).setDefinitionAlreadyTaken(false);
                        }

                    }
                    res.getWriter().write(guessResponse);
                }
            }
            else{
                res.getWriter().write("You're late! other guessers already guessed all your turn words.");
            }
        }
    }

    private static String giveGuessResponse(Guess guess) {
        if (guess.isTurnPassed()) {
            return "Turn passed to the next team.";
        }
        if (!guess.isWordOnBoard()) {
            return "Word is not on board. Please make another guess.";
        }
        if (guess.isWordAlreadyFound()) {
            return "This word has already been discovered! Please make another guess.";
        }
        if (guess.isGuessedForOtherTeam()) {
            return "Wrong! This word belongs to " + guess.getTeamNameOnCard();
        }
        if (guess.isGuessCorrect()) {
            return "Correct!";
        }
        if (guess.isGuessedWordBlack()) {
            return "Black word! You lose!";
        }
        if (guess.isGuessedWordWithoutTeam()) {
            return "Wrong! This word belongs to no team.";
        }
        return "";
    }

    private void decrementUsersAndCheckToResetGame(int gameIndex){
        engine.getAllGamesList().get(gameIndex).decementUserCounter();
        if(engine.getAllGamesList().get(gameIndex).getUsersCounter() == 0){
            engine.resetGame(gameIndex);
        }
    }
}


