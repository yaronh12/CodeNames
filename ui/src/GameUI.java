import engine.Engine;
import engine.EngineImpl;
import team.Team;
import components.*;

public class GameUI {
    private Engine game = new EngineImpl();

    public void printGameData(){
        System.out.println("Game Details:");
        System.out.println("There are " + game.getRegularWords().size() + " words to choose from.");
        System.out.println("There are " + game.getBlackWords().size() + " black words to choose from.");
        System.out.println("We'll play " + game.getHowManyRegularWordsInGame() + " regular words and " + game.getHowManyBlackWordsInGame() + " black words.");
        for(Team team: game.getTeams()){
            System.out.println(team);
        }

    }
    public void printCards(){
        for (int i = 0; i < game.getBoardState().size(); i++) {
            System.out.println((i+1)+". "+game.getBoardState().get(i).toString());
        }
        System.out.println("There are " + game.getBoardState().size() + " cards");

    }
}
