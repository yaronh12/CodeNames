import callconfig.CallConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ui.GeneralGameInfo;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;

import java.io.IOException;

public class WatchLiveGame {

    public static JsonArray displayActiveGames(){
        String RESOURCE = "/live-games";
        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){

                JsonArray activeGamesJson = JsonParser.parseString(responseBody).getAsJsonArray();
                return GeneralGameInfo.printAllActiveGames(activeGamesJson);

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

    public static boolean displayLiveGameByChoice(int adminChoice){
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
                //System.out.println("Request Failed: Code " + response.code());
                System.out.println(responseBody);
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }




}
