package engine.game;

public class PendingGameInfo {
    int gameIndex;
    Game game;

    public PendingGameInfo(int gameIndex, Game game) {
        this.gameIndex = gameIndex;
        this.game = game;
    }
}
