import callconfig.CallConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import ui.BoardPrinter;
import ui.GeneralGameInfo;

import java.io.IOException;

public class LiveGameStatus {
    public static boolean getLiveGameStatus(int gameIndex,int teamIndex, BoardPrinter.Role role){
        String RESOURCE = "/live-game-status";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CallConfig.BASE_URL + RESOURCE).newBuilder();
        urlBuilder.addQueryParameter("Live game status index", Integer.toString(gameIndex));
        urlBuilder.addQueryParameter("team index", Integer.toString(teamIndex));
        //urlBuilder.addQueryParameter("role",Integer.toString(role));
        String finalUrl = urlBuilder.build().toString();
        boolean isTeamFinished = false;
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response =  CallConfig.HTTP_CLIENT.newCall(request).execute()){
            String responseBody = response.body().string();
            if(response.isSuccessful()){
                JsonObject game = JsonParser.parseString(responseBody).getAsJsonObject();
                //GeneralGameInfo.printActiveGameInfo(liveGame);
                GeneralGameInfo.printLiveGameStatus(game,role);
            }
            else{
                //System.out.println("Request Failed: Code " + response.code());
                System.out.println(responseBody);
                isTeamFinished = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return isTeamFinished;
    }
}
