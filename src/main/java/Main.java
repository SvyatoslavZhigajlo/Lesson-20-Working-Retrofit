import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.runGoogle();
            main.runGitHub();
            main.runTinyURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runGoogle() throws IOException {

        GoogleWebService service = new Retrofit.Builder()
                .baseUrl("https://google.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(GoogleWebService.class);
        Response<String> response = null;
        try {
            response = service.home().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            System.out.println(response.body());
        } else {
            System.out.println(response.errorBody().string());
        }
    }

    private void runGitHub() throws IOException {
        GitHubService service = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(GitHubService.class);
        Response<String> responseGitHub = service.getRepositories("octocat").execute();

        if (responseGitHub.isSuccessful()) {
            System.out.println(responseGitHub.body());
        }else {
            System.out.println(responseGitHub.errorBody().string());
        }
    }

    private void runTinyURL() throws IOException {
        final TinyUrlService service = new Retrofit.Builder()
                .baseUrl("http://tiny-url.info/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TinyUrlService.class);
        Response <TinyUrlResponse> responseTiny = null;
        try {
            responseTiny = service.random("json", "https://kiev.vgorode.ua/news/sobytyia/a1188559-mozhno-li-oformit-kartu-kievljanina-bez-propiski-v-kieve").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseTiny != null && responseTiny.isSuccessful()){
            System.out.println(responseTiny.body().shortUrl);
        }else if(responseTiny != null){
            System.out.println(responseTiny.errorBody().string());
        }
    }
}
