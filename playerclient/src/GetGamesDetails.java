import callconfig.CallConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.Response;
import ui.GeneralGameInfo;

import java.io.IOException;

import static utils.Utils.UserType.ADMIN;
import static utils.Utils.UserType.PLAYER;

public class GetGamesDetails {
    public static void getGamesDetails(){
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
                if(gamesList.isEmpty())
                    System.out.println("No game is system!");
                else
                    GeneralGameInfo.printAllGamesInfo(gamesList, PLAYER);
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
