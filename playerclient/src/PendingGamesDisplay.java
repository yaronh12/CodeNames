import callconfig.CallConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.Response;
import ui.GeneralGameInfo;
import utils.Utils;

import java.io.IOException;

public class PendingGamesDisplay {
    public static JsonArray displayPendingGames(){
        String RESOURCE = "/pending-games";
        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){

                JsonArray pendingGamesJson = JsonParser.parseString(responseBody).getAsJsonArray();
                GeneralGameInfo.printAllPendingGamesInfo(pendingGamesJson);
                if(!pendingGamesJson.isEmpty())
                    return pendingGamesJson;

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
}
