import callconfig.CallConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import ui.BoardPrinter;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;


public class PlayTurn {
    public static boolean askToPlayTurn(BoardPrinter.Role role, int gameIndex, int teamIndex){
        boolean isGameOver = false;
        String RESOURCE = "/play-turn";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("role", role.toString());
        urlBuilder.addQueryParameter("game index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("team index", Integer.toString(teamIndex));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
                //it is the player's turn!

                    System.out.println("Your turn it is!");
                    System.out.println("The current board:");
                    JsonObject game = JsonParser.parseString(responseBody).getAsJsonObject();
                    JsonObject board = game.get("board").getAsJsonObject();
                    BoardPrinter.displayBoard(board.get("cards").getAsJsonArray()
                            , board.get("rows").getAsInt()
                            , board.get("cols").getAsInt()
                            , role);
                    if (role == BoardPrinter.Role.SPYMASTER) {
                        giveClue(gameIndex, game.get("teams").getAsJsonArray().get(teamIndex).getAsJsonObject().get("cardsAmount").getAsInt() - game.get("teams").getAsJsonArray().get(teamIndex).getAsJsonObject().get("score").getAsInt());
                    } else {
                        isGameOver = takeGuess(gameIndex, board.get("cards").getAsJsonArray().size(), game.get("currentClueWord").getAsString());
                    }

            }


            else{
                //System.out.println("Request Failed: Code " + response.code());
                if(response.code() == 406)
                    isGameOver = true;
                System.out.println(responseBody);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return isGameOver;
    }

    private static boolean takeGuess(int gameIndex, int wordsAmount, String clue){
        System.out.println("Your clue is: " + clue);
        System.out.println("Enter your guess word index:");
        int guessIndex = Utils.getValidInteger(1,wordsAmount);

        String RESOURCE = "/play-turn";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("game index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("guess index", Integer.toString(guessIndex));
        String finalUrl = urlBuilder.build().toString();

        RequestBody body = new FormBody.Builder()
                .add("game index", Integer.toString(gameIndex))
                .add("guess index", Integer.toString(guessIndex))
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
                //System.out.println("Request Failed: Code " + response.code());
                System.out.println(responseBody);
                if(response.code() == 406)
                    return false;
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;


    }

    private static void giveClue(int gameIndex, int cardsLeft){
        Scanner in = new Scanner(System.in);
        System.out.println("Give your clue:");
        String clueString = in.nextLine();
        System.out.println("How many words?");
        int howManyWords = Utils.getValidInteger(1,cardsLeft);

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
