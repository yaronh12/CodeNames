import callconfig.CallConfig;
import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class SignUpToSystem {
    private static String username;
    public static boolean signUpToSystem(){
        boolean isSignupSuccedded;
        username = scanPlayerName();
        String RESOURCE = "/system-signup";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("username", username);
        String finalUrl = urlBuilder.build().toString();
        String json = "{\"username\": \"newuser\"}";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                System.out.println(responseBody);
                isSignupSuccedded = true;
            }
            else{
                //System.out.println("Request Failed: Code " + response.code());
                System.out.println("Error messege: " + responseBody);
                isSignupSuccedded = false;
            }
        } catch (IOException e) {
            isSignupSuccedded = false;
            throw new RuntimeException(e);
        }
        return isSignupSuccedded;
    }

    private static String scanPlayerName(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter username:");
        String name = in.nextLine();
        return name;
    }

    public static String getUsername(){
        return username;
    }
}
