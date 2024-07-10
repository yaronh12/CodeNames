import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class DisplayGamesDetails {
    public static void displayGamesDetails(){
        String RESOURCE = "/games-details";
        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                //System.out.println(responseBody);
                JsonArray gamesList = JsonParser.parseString(responseBody).getAsJsonArray();
                JsonObject currGame;
                for(int i=0;i<gamesList.size();i++){
                    currGame = gamesList.get(i).getAsJsonObject();
                    System.out.println("------Game #"+(i+1)+"------");
                    System.out.println("Name: "+currGame.get("gameName").getAsString());
                    System.out.println("Status: "+(currGame.get("isGameActive").getAsBoolean() ? "Active" : "Pending"));
                    System.out.println("Board Layout: "+currGame.get("board").getAsJsonObject().get("rows").getAsInt() + " X " +
                                                         currGame.get("board").getAsJsonObject().get("cols").getAsInt());
                    System.out.println("Text file: " + currGame.get("txtFileName").getAsString() + " contains "+
                                                        currGame.get("totalWordsInFile").getAsInt() + " unique words.");
                    System.out.println("Regular words: "+currGame.get("board").getAsJsonObject().get("regularWordsAmount").getAsInt()
                                        +", Black words: "+currGame.get("board").getAsJsonObject().get("blackWordsAmount").getAsInt());
                    JsonArray teamsList = currGame.get("teams").getAsJsonArray();
                    JsonObject currTeam;
                    for(int j=0;j<teamsList.size();j++){
                        currTeam = teamsList.get(j).getAsJsonObject();
                        System.out.println("Team #"+(j+1)+":");
                        System.out.println("\tName: " + currTeam.get("name").getAsString());
                        System.out.println("\tWords Amount: " + currTeam.get("cardsAmount").getAsInt());
                        System.out.println("\tDefiners amount: " + currTeam.get("definersAmount").getAsInt());
                        System.out.println("\tGuessers amount: " + currTeam.get("guessersAmount").getAsInt());
                    }
                }
            }
            else{
                System.out.println("Request Failed: Code " + response.code());
                System.out.println("Error messege: " + responseBody);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
