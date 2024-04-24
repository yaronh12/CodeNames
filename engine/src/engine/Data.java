package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {

    private int cardsCount = 24;
    private int blackCardsCount = 1;

    private int rows = 5;
    private int cols = 5;


    private int team1cards = 9;

    private String team1name = "T1";
    private int team2cards = 8;
    private String team2name = "T2";

    public int getNumOfTeam() {
        return numOfTeam;
    }

    private int numOfTeam = 2;

    private Set<String> wordsSet = new HashSet<String>() {{
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
    private Set<String> blackWordsSet = new HashSet<String>() {{
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

    public int getTeam1cards() {
        return team1cards;
    }

    public String getTeam1name() {
        return team1name;
    }

    public int getTeam2cards() {
        return team2cards;
    }

    public String getTeam2name() {
        return team2name;
    }

    public Set<String> getWordsSet() {
        return wordsSet;
    }

    public Set<String> getBlackWordsSet() {
        return blackWordsSet;
    }
}