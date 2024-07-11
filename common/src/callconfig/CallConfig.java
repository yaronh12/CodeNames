package callconfig;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CallConfig {
    public final static String BASE_URL = "http://localhost:8080/webapp-codenames";
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public static Callback SIMPLE_CALLBACK = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.out.println("Ooops... something went wrong... error: " + e.getMessage());
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            System.out.println("Response:");
            System.out.println(response.body().string());
        }
    };
}
//adam.kowen@gmail.com
//google!20899Telaviv