import callconfig.CallConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import ui.GeneralGameInfo;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;

import java.io.IOException;

import static utils.Utils.UserType.ADMIN;

public class DisplayGamesDetails {
    public static void displayGamesDetails(){
        String RESOURCE = "/games-details";
       /* HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("Live game choice", ADMIN.toString());
        String finalUrl = urlBuilder.build().toString();*/
        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                JsonArray gamesList = JsonParser.parseString(responseBody).getAsJsonArray();
                GeneralGameInfo.printAllGamesInfo(gamesList, ADMIN);
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