import Client.SpotifySearchResponseObject;
import Client.TokenClient;
import Model.*;
import com.google.gson.Gson;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

    @FXML private AnchorPane homeScreen;
    @FXML private TextField searchTextField;
    @FXML SplitPane resultsPanel;
    private Gson gson;
    private HttpClient client;
    private TokenClient tokenClient;
    private SuggestionProvider<String> provider;
    private AutoCompletionTextFieldBinding<String> autoCompletionTextFieldBinding;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gson = new Gson();
        client = HttpClient.newHttpClient();
        tokenClient = new TokenClient();
        provider = SuggestionProvider.create(Collections.emptyList());
        autoCompletionTextFieldBinding =  new AutoCompletionTextFieldBinding<>(searchTextField, provider);
    }

    @FXML
    private void search() {
        String rawSearchParameter = searchTextField.getText();
        String searchParameter = rawSearchParameter.replaceAll(" ", "%20");
        searchParameter = '"'+searchParameter+'"';
        try {
            String spotifyApiToken = tokenClient.getSpotifyToken();
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
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            Map<SpotifyObject,String> spotifyObjects = new HashMap<>();
            for (SpotifyObject spotifyObject : spotifySearchResponseObject.getAllSpotifyResponseObjects()) {
                spotifyObjects.put(spotifyObject, spotifyObject.getName());
            }
            provider.clearSuggestions();
            provider.addPossibleSuggestions(spotifyObjects.values());
            autoCompletionTextFieldBinding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {
                @Override
                public void handle(AutoCompletionBinding.AutoCompletionEvent<String> event) {
                    SpotifyObject spotifyObject = null;
                    for  (Map.Entry<SpotifyObject,String> entry : spotifyObjects.entrySet()) {
                        if (searchTextField.getText().equals(entry.getValue())) {
                            if (entry.getKey().getType().equals("artist")) {
                                Artist artist = (Artist) entry.getKey();
                                System.out.println(artist.getFollowers().getSpotifyFollowers());
                                loadArtistPanel(artist);
                            }
                            if (entry.getKey().getType().equals("track")) {
                                Track track = (Track) entry.getKey();
                                //TODO create track panel
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
            String spotifyApiToken = tokenClient.getSpotifyToken();
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

    private void loadArtistPanel(Artist artist) {
        try {
            FXMLLoader artistPanelLoader = new FXMLLoader(getClass().getClassLoader().getResource("ArtistPanel.fxml"));
            resultsPanel.getItems().remove(1);
            resultsPanel.getItems().add(artistPanelLoader.load());
            ArtistPanelController artistPanelController = artistPanelLoader.getController();
            artistPanelController.populateArtistPanel(artist);
            artistPanelController.populateSongsAndAlbums();

        }
        catch (IOException exception) {

        }




    }
}
