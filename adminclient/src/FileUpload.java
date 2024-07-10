import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class FileUpload {
    public static boolean oneFileGotUploaded = false;

    public static void uploadFile(){
        Scanner in = new Scanner(System.in);
        String RESOURCE = "/upload-file";
        System.out.println("Enter file:");
        String filename = in.nextLine();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","filename.txt",
                        RequestBody.create(filename, MediaType.parse("text/plain")))
                .build();

        Request request = new Request.Builder()
                .url(CallConfig.BASE_URL + RESOURCE)
                .post(requestBody)
                .build();

        try(Response response = CallConfig.HTTP_CLIENT.newCall(request).execute()){

            String responseBody = response.body().string();
            //System.out.println(responseBody);
            if(response.isSuccessful()){
                System.out.println("Response: " + responseBody);
                oneFileGotUploaded = true;
            }
            else{
                System.out.println("Request failed: " + response.code());
                System.out.println("Request error messege: " + responseBody);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
