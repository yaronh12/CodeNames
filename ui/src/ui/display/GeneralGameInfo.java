package ui.display;

import engine.engine.Engine;
import team.team.Team;
import team.team.TeamsInfo;

import java.util.List;

import static ui.display.BoardPrinter.displayBoard;

/**
 * Manages the display of game-related information on the console, providing methods to print game details
 * and active game state including team scores and game progress.
 */
public class GeneralGameInfo {

    /**
     * Prints detailed information about the current game state including the scoreboard and turns played.
     * This method pulls information from the game engine and formats it for display, enhancing user experience
     * by providing clear and structured game status updates.
     *
     * @param game The game engine from which to retrieve game data.
     */
    public static void printActiveGameInfo(Engine game){
        TeamsInfo teamsInfo = game.getTeamsInfo(0);
        displayBoard(game.getBoardState(0), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.SPYMASTER);
        List<String> teamNames = teamsInfo.getTeamNames();
        List<String> teamScores = teamsInfo.getTeamsCurrentScore();
        List<Integer> teamTurnCounter = teamsInfo.getTeamsTurnCounter();
        System.out.println("Teams Information:");
        for(int i = 0; i < teamsInfo.getHowManyTeams(); i++){
            System.out.println("Team #" + (i+1));
            System.out.println(" - Name: " + teamNames.get(i));
            System.out.println(" - Score: " + teamScores.get(i));
            System.out.println(" - Turns played: " + teamTurnCounter.get(i));
        }
        System.out.println("Next turn is " + teamsInfo.getNextTeamName());
    }

    /**
     * Prints an overview of the game details, including the total number of words available for play,
     * both regular and black words, and the number of each type that will be used in the game.
     * This method provides a clear and concise summary of the game setup, which is useful for players to understand
     * the scope and rules of the game at a glance.
     *
     * @param game The game engine that provides access to the overall game configuration.
     */
    public static void printGameData(Engine game) {
        System.out.println("--------------------------");
        System.out.println("Game Details:");
        /*System.out.println("There are " + game.getRegularWords().size() + " words to choose from.");
        System.out.println("There are " + game.getBlackWords().size() + " black words to choose from.");*/
        System.out.println("We'll play " + game.getHowManyRegularWordsInGame() + " regular words and " + game.getHowManyBlackWordsInGame() + " black words.");
        for (Team team : game.getTeams()) {
            System.out.println(team);
        }
        System.out.println("--------------------------");
    }
}