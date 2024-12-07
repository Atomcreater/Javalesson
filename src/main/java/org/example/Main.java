import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Company;
import org.example.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        String usersUrl = "https://fake-json-api.mock.beeceptor.com/users";
        String companiesUrl = "https://fake-json-api.mock.beeceptor.com/companies";

        try {
            String usersResponse = sendHttpRequest(usersUrl);
            String companiesResponse = sendHttpRequest(companiesUrl);

            System.out.println("Users:");
            parseAndPrintJson(usersResponse, new TypeToken<List<User>>(){}.getType());

            System.out.println("\nCompanies:");
            parseAndPrintJson(companiesResponse, new TypeToken<List<Company>>(){}.getType());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String sendHttpRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Метод для парсинга и вывода JSON
    private static <T> void parseAndPrintJson(String json, Type typeOfT) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        T data = gson.fromJson(json, typeOfT);
        System.out.println(gson.toJson(data));
    }

}