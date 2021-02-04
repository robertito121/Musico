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
                    SpotifyObject spotifyObject = null;
                    for  (Map.Entry<SpotifyObject,String> entry : spotifyObjectMap.entrySet()) {
                        if (searchTextField.getText().equals(entry.getValue())) {
                            spotifyObject = entry.getKey();
                        }
                    }

                }
            });
        }
        catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private SpotifyObject getSpotifyObjectInfo(SpotifyObject spotifyObject) {
        String searchResponse = "";
        String api_path = "";
        if (spotifyObject.getType().equals("artist")) {
            api_path = "artists";
        }
        if (spotifyObject.getType().equals("track")) {
            api_path = "tracks";
        }
        try {
            String spotifyApiToken = tokenService.getSpotifyToken();
            URI searchUri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.spotify.com")
                    .setPath("v1/" + api_path)
                    .addParameter("ids", spotifyObject.getId())
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyApiToken)
                    .build();
            searchResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }
        catch (URISyntaxException | InterruptedException | IOException exception) {
            exception.getStackTrace();
        }

        if (api_path.equals("artists")) {
            ArtistList artistList = gson.fromJson(searchResponse, ArtistList.class);
            return artistList.getArtists().get(0);

        }
        else if (api_path.equals("tracks")) {
            TrackList trackList = gson .fromJson(searchResponse, TrackList.class);
            return trackList.getTracks().get(0);
        }
        else {
            return null;
        }
    }

    private void loadResultsPanel() {

    }
}
