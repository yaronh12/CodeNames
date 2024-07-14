import callconfig.CallConfig;
import okhttp3.*;
import ui.BoardPrinter;

import java.io.IOException;

public class RegisterToGame {
    public static void registerToGame(int gameIndex, int teamIndex, BoardPrinter.Role role){
        String RESOURCE = "/register-to-game";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("game index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("team index", Integer.toString(teamIndex));
        urlBuilder.addQueryParameter("role",role.toString());
        String finalUrl = urlBuilder.build().toString();

        RequestBody body = new FormBody.Builder()
                .add("game index", Integer.toString(gameIndex))
                .add("team index", Integer.toString(teamIndex))
                .add("role", role.toString())
                .build();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()) {
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                System.out.println(responseBody);
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
