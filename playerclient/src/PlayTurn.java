import callconfig.CallConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import ui.BoardPrinter;
import ui.GeneralGameInfo;

import java.io.IOException;
import java.util.Scanner;

public class PlayTurn {
    public static void askToPlayTurn(BoardPrinter.Role role, int gameIndex, int teamIndex){
        String RESOURCE = "/play-turn";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("role", role.toString());
        urlBuilder.addQueryParameter("game index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("team index", Integer.toString(teamIndex));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                //it is the player's turn!
                System.out.println(responseBody);
                if(role == BoardPrinter.Role.SPYMASTER){
                    giveClue(gameIndex);
                }
                else{
                    //takeGuess(gameIndex);
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

    private static void giveClue(int gameIndex){
        Scanner in = new Scanner(System.in);
        System.out.println("Give your clue:");
        String clueString = in.nextLine();
        System.out.println("How many words?");
        int howManyWords = in.nextInt();

        String RESOURCE = "/play-turn";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("game index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("clue word",clueString);
        urlBuilder.addQueryParameter("how many words", Integer.toString(howManyWords));
        String finalUrl = urlBuilder.build().toString();

        RequestBody body = new FormBody.Builder()
                .add("game index", Integer.toString(gameIndex))
                .add("clue word", clueString)
                .add("how many words", Integer.toString(howManyWords))
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
