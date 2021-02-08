import Client.SpotifyClient;
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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
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
    private SpotifyClient spotifyClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gson = new Gson();
        client = HttpClient.newHttpClient();
        tokenClient = new TokenClient();
        provider = SuggestionProvider.create(Collections.emptyList());
        autoCompletionTextFieldBinding =  new AutoCompletionTextFieldBinding<>(searchTextField, provider);
        spotifyClient = new SpotifyClient(tokenClient);
    }

    @FXML
    private void search() throws InterruptedException, IOException, URISyntaxException {
        String searchParameter = searchTextField.getText();
        String spotifyToken = tokenClient.getSpotifyToken();
        Map<SpotifyObject,String> spotifyObjects = spotifyClient.searchSpotify(searchParameter, spotifyToken);
        provider.clearSuggestions();
        provider.addPossibleSuggestions(spotifyObjects.values());
        autoCompletionTextFieldBinding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {
            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<String> event) {
                provider.clearSuggestions();
                for  (Map.Entry<SpotifyObject,String> entry : spotifyObjects.entrySet()) {
                    if (searchTextField.getText().equals(entry.getValue())) {
                        if (entry.getKey().getType().equals("artist")) {
                            Artist artist = (Artist) entry.getKey();
                            ArrayList<Track> tracks = spotifyClient.searchPopularTracksByArtist(artist.getName(), spotifyToken);
                            ArrayList<Album> albums = spotifyClient.searchAlbumByArtist(artist.getName(), spotifyToken);
                            loadArtistPanel(artist, tracks, albums);

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

    private void loadArtistPanel(Artist artist, ArrayList<Track> tracks, ArrayList<Album> albums) {
        try {
            FXMLLoader artistPanelLoader = new FXMLLoader(getClass().getClassLoader().getResource("ArtistPanel.fxml"));
            resultsPanel.getItems().remove(1);
            resultsPanel.getItems().add(artistPanelLoader.load());
            ArtistPanelController artistPanelController = artistPanelLoader.getController();
            artistPanelController.populateArtistPanel(artist);
            artistPanelController.populateSongsAndAlbums(tracks, albums);
        }
        catch (IOException exception) {
            exception.printStackTrace();

        }




    }
}
