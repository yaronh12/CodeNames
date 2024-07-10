package engine.game;

public class ActiveGameInfo {
    int gameIndex;
    String gameName;
    int readyTeamsAmount;
    int totalTeamsAmount;

    public ActiveGameInfo(int gameIndex, String gameName, int readyTeamsAmount, int totalTeamsAmount) {
        this.gameIndex = gameIndex;
        this.gameName = gameName;
        this.readyTeamsAmount = readyTeamsAmount;
        this.totalTeamsAmount = totalTeamsAmount;
    }
}
