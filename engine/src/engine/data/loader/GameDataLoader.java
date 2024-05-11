package engine.data.loader;

import components.board.Board;
import generated.ECNGame;
import team.team.Team;
import java.util.*;

public class GameDataLoader {
    ECNGame gameData;

    public GameDataLoader(ECNGame gameData){
        this.gameData = gameData;
    }
    public Board initializeBoard(List<Team> teams){
        List<String> regularWords = Arrays.asList(this.gameData.getECNWords().getECNGameWords().trim().split("\\s+"));
        System.out.println(regularWords);
       // regularWords.remove(0);
        List<String> blackWords = Arrays.asList(this.gameData.getECNWords().getECNBlackWords().trim().split("\\s+"));
      //  blackWords.remove(0);
        int regularWordsAmount = gameData.getECNBoard().getCardsCount();
        int blackWordsAmount = gameData.getECNBoard().getBlackCardsCount();
        int rows = gameData.getECNBoard().getECNLayout().getRows();
        int cols = gameData.getECNBoard().getECNLayout().getColumns();
        return new Board(regularWords,blackWords,regularWordsAmount,blackWordsAmount,rows,cols,teams);
    }

    public List<Team> initializeTeams(){
        List<Team> teams = new ArrayList<>();

        teams.add(new Team(gameData.getECNTeam1().getName(), gameData.getECNTeam1().getCardsCount()));
        teams.add(new Team(gameData.getECNTeam2().getName(), gameData.getECNTeam2().getCardsCount()));

        return teams;
    }

    /*
    private int cardsCount = 24;
    private int blackCardsCount = 1;

    private int rows = 5;
    private int cols = 5;

    private List<String> teamsNames = new ArrayList<String>(){{
        add("T1");
        add("T2");
    }};
    private List<Integer> howManyWordsForEachTeam = new ArrayList<Integer>(){{
        add(9);
        add(8);
    }};

    public List<String> getTeamsNames(){
        return teamsNames;
    }

    public List<Integer> getHowManyWordsForEachTeam(){
        return howManyWordsForEachTeam;
    }

    public int getNumOfTeam() {
        return numOfTeam;
    }

    private int numOfTeam = 2;

    private List<String> wordsList = new ArrayList<String>() {{
        add("encapsulation");
        add("german");
        add("poland");
        add("kombat");
        add("waffele");
        add("whom");
        add("leg");
        add("character");
        add("terms");
        add("else");
        add("camel");
        add("rabbit");
        add("fire");
        add("text");
        add("element");
        add("sky");
        add("item");
        add("robot");
        add("past");
        add("dune");
        add("dolphine");
        add("then");
        add("it");
        add("am");
        add("quality");
        add("eye");
        add("moon");
        add("system");
        add("folder");
        add("light");
        add("letter");
        add("number");
        add("notch");
        add("allies");
        add("dog");
        add("word");
        add("hash");
        add("why");
        add("gun");
        add("pink");
        add("what");
        add("yellow");
        add("sea");
        add("see");
        add("noon");
        add("file");
        add("top");
        add("does");
        add("ear");
        add("can't");
        add("table");
        add("foot");
        add("hand");
        add("a");
        add("strike");
        add("black");
        add("electricity");
        add("pistol");
        add("kill");
        add("boat");
        add("democracy");
        add("off");
        add("battle");
        add("single");
        add("fly");
        add("component");
        add("blue");
        add("watch");
        add("reflector");
        add("future");
        add("machine");
        add("progress");
        add("atom");
        add("under");
        add("inferno");
        add("voice");
        add("rotor");
        add("tonight");
    }};
    private List<String> blackWordsList = new ArrayList<String>() {{
        add("mouse");
        add("door");
        add("data");
        add("use");
        add("doom");
        add("sign");
        add("screen");
        add("do");
        add("bomb");
        add("red");
        add("later");
        add("whale");
        add("white");
        add("hello");
        add("than");
        add("which");
        add("england");
        add("morning");
        add("dirt");
        add("tree");
        add("thrown");
        add("water");
        add("trash");
        add("ranch");
        add("she");
        add("sand");
        add("squad");
        add("desk");
        add("present");
        add("where");
        add("magenta");
        add("game");
        add("code");
        add("enigma");
        add("plural");
        add("death");
        add("vegetable");
        add("fruit");
        add("for");
        add("their");
        add("privacy");
        add("back");
        add("house");
        add("fox");
        add("not");
        add("hair");
        add("napkin");
        add("and");
        add("street");
        add("cat");
        add("now");
        add("of");
        add("iteration");
        add("class");
        add("trike");
        add("fight");
        add("mortal");
        add("live");
        add("how");
        add("on");
        add("patrol");
        add("keyboard");
        add("midnight");
        add("or");
        add("green");
        add("umbrella");
        add("wheel");
    }};

    public int getCardsCount() {
        return cardsCount;
    }

    public int getBlackCardsCount() {
        return blackCardsCount;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }



    public List<String> getWordsList() {
        return wordsList;
    }

    public List<String> getBlackWordsList() {
        return blackWordsList;
    }*/
}