import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

public class Assignment127 {
    public static void main(String[] args){
        HTTPClient client = new HTTPClient();
        try{
            client.get("https://google.com");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

class HTTPClient{
    public void get(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println(response.body());
    }
}