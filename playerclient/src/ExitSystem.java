import callconfig.CallConfig;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ExitSystem {
    public static void exitSystem(String username){
        String RESOURCE = "/exit-system";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("username", username);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
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
