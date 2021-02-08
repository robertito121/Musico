package Client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Spotify Web Service Token Client
 */
public class TokenClient {

    private HttpClient client;
    private Gson gson;
    private final String SPOTIFY_AUTH_TOKEN = System.getenv("AUTH_TOKEN");

    public TokenClient() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    /**
     * returns a new bearer access_token to be used for Authentication to Spotify Web Server
     * @return String
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public String getSpotifyToken() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://accounts.spotify.com/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .header("Authorization", SPOTIFY_AUTH_TOKEN)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        return "Bearer " + jsonObject.get("access_token").toString().replace("\"", "");
    }
}
