import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import display.GeneralGameInfo;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;

public class WatchLiveGame {

    public static JsonArray displayActiveGames(){
        String RESOURCE = "/live-games";
        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
               // Gson gson = new Gson();
                //Type mapType = new TypeToken<Map<Integer, ActiveGameInfo>>(){}.getType();
                JsonArray activeGamesJson = JsonParser.parseString(responseBody).getAsJsonArray();
                if(activeGamesJson.size() != 0) {
                    JsonObject currGame;
                    for (int i = 0; i < activeGamesJson.size(); i++) {
                        currGame = activeGamesJson.get(i).getAsJsonObject();
                        System.out.println("Game #" + (i + 1) + ":");
                        System.out.println("\tName: " + currGame.get("gameName").getAsString());
                        System.out.println("\t" + currGame.get("readyTeamsAmount").getAsInt() + " / " +
                                currGame.get("totalTeamsAmount").getAsInt() + " teams are ready to play");

                    }
                    return activeGamesJson;
                }
                else{
                    System.out.println("No active games!");
                    return null;
                }

            }
            else{
                System.out.println("Request Failed: Code " + response.code());
                System.out.println("Error messege: " + responseBody);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void displayLiveGameByChoice(int adminChoice){
        String RESOURCE = "/live-games";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("Live game choice", Integer.toString(adminChoice));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                JsonObject liveGame = JsonParser.parseString(responseBody).getAsJsonObject();
                GeneralGameInfo.printActiveGameInfo(liveGame);
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
