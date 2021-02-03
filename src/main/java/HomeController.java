import Model.*;
import com.google.gson.Gson;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.http.client.utils.URIBuilder;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class HomeController implements Initializable {

    @FXML private TextField searchTextField;
    private Gson gson;
    private HttpClient client;
    private TokenService tokenService;
    private SuggestionProvider<String> provider;
    private AutoCompletionTextFieldBinding<String> autoCompletionTextFieldBinding;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gson = new Gson();
        client = HttpClient.newHttpClient();
        tokenService = new TokenService();
        provider = SuggestionProvider.create(Collections.emptyList());
        autoCompletionTextFieldBinding =  new AutoCompletionTextFieldBinding<>(searchTextField, provider);
    }

    @FXML
    private void search() {
        String rawSearchParameter = searchTextField.getText();
        String searchParameter = rawSearchParameter.replaceAll(" ", "%20");
        searchParameter = '"'+searchParameter+'"';
        try {
            String spotifyApiToken = tokenService.getSpotifyToken();
            URI searchUri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.spotify.com")
                    .setPath("v1/search")
                    .addParameter("q", searchParameter)
                    .addParameter("type", "track,artist")
                    .addParameter("limit", "5")
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyApiToken)
                    .build();
            String searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifyResponseObject spotifyResponseObject = gson.fromJson(searchResponse, SpotifyResponseObject.class);
            Map<SpotifyObject,String> spotifyObjectMap = new HashMap<>();
            for (SpotifyObject spotifyObject : spotifyResponseObject.getAllSpotifyResponseObjects()) {
                spotifyObjectMap.put(spotifyObject, spotifyObject.getName());
            }
            provider.clearSuggestions();
            provider.addPossibleSuggestions(spotifyObjectMap.values());
            autoCompletionTextFieldBinding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {
                @Override
                public void handle(AutoCompletionBinding.AutoCompletionEvent<String> event) {
                    String spotifyObjectId = "";
                    String spotifyObjectType = "";
                    for  (Map.Entry<SpotifyObject,String> entry : spotifyObjectMap.entrySet()) {
                        if (searchTextField.getText().equals(entry.getValue())) {
                            spotifyObjectId = entry.getKey().getId();
                            spotifyObjectType = entry.getKey().getType();
                            if (spotifyObjectType.equals("artist")) {
                                Artist artist = getArtistById(spotifyObjectId);
                            }
                            if (spotifyObjectId.equals("track")) {
                                Track track = getTrackById(spotifyObjectId);
                            }

                        }
                    }
                }
            });
        }
        catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private Artist getArtistById(String id) {
        Artist artist = null;
        try {
            String spotifyApiToken = tokenService.getSpotifyToken();
            URI searchUri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.spotify.com")
                    .setPath("v1/artists")
                    .addParameter("ids", id)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyApiToken)
                    .build();
            String searchResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            ArtistList artistList = gson.fromJson(searchResponse, ArtistList.class);
            artist = artistList.getArtists().get(0);
        }
        catch (URISyntaxException uriSyntaxException) {

        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return artist;
    }

    private Track getTrackById(String id) {
        Track track = null;
        try {
            String spotifyApiToken = tokenService.getSpotifyToken();
            URI searchUri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.spotify.com")
                    .setPath("v1/tracks")
                    .addParameter("ids", id)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyApiToken)
                    .build();
            String searchResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            TrackList trackList = gson.fromJson(searchResponse, TrackList.class);
            track = trackList.getTracks().get(0);
        }
        catch (URISyntaxException | IOException | InterruptedException exception) {

        }
        return track;
    }
}
