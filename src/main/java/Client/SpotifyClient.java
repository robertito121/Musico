package Client;

import Model.Album;
import Model.Track;
import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SpotifyClient {

    private final String URI_SCHEME = "https";
    private final String URI_HOST = "api.spotify.com";
    private HttpClient client;
    private Gson gson;

    public SpotifyClient() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public ArrayList<Track> searchPopularTracksByArtist(String artistName, String spotifyApiToken) {
        URI searchUri = null;
        String searchResponse = "";
        ArrayList<Track> tracks = new ArrayList<>(9);
        String searchParameter = artistName.replaceAll(" ", "%20");
        searchParameter = "artist:" + '"'+searchParameter+'"';
        try {
            searchUri = new URIBuilder()
                    .setScheme(URI_SCHEME)
                    .setHost(URI_HOST)
                    .setPath("v1/search")
                    .addParameter("q", searchParameter)
                    .addParameter("type", "track")
                    .addParameter("limit", "50")
                    .build();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(searchUri)
                .header("Authorization", spotifyApiToken)
                .build();
        try {
            searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            tracks = spotifySearchResponseObject.getTracksList().getTracks().stream()
                    .sorted(Comparator.comparing(Track::getPopularity))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        tracks.trimToSize();
        return tracks;
    }

    private ArrayList<Album> searchAlbumByArtist(String artistName, String spotifyApiToken) {
        URI searchUri = null;
        String searchResponse = "";
        ArrayList<Album> albums = new ArrayList<>(3);
        String searchParameter = artistName.replaceAll(" ", "%20");
        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        searchParameter = "artist:" + '"'+searchParameter+'"' + "%20year:" +
                            (Integer.parseInt(currentYear) - 10) + "-" + currentYear;
        try {
            searchUri = new URIBuilder()
                    .setScheme(URI_SCHEME)
                    .setHost(URI_HOST)
                    .setPath("v1/search")
                    .addParameter("q", searchParameter)
                    .addParameter("type", "album")
                    .addParameter("limit", "50")
                    .build();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(searchUri)
                .header("Authorization", spotifyApiToken)
                .build();
        try {
            searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            albums = spotifySearchResponseObject.getAlbumList().getAlbums();
        }
        catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        albums.trimToSize();
        return  albums;
    }
}
