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
