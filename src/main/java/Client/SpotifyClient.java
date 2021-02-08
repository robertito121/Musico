package Client;

import Model.Album;
import Model.SpotifyObject;
import Model.Track;
import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;


public class SpotifyClient {

    private final String URI_SCHEME = "https";
    private final String URI_HOST = "api.spotify.com";
    private HttpClient client;
    private Gson gson;

    public SpotifyClient(TokenClient tokenClient) {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public Map<SpotifyObject,String> searchSpotify(String searchParameter, String spotifyToken) {
        Map<SpotifyObject,String> spotifyObjects = new HashMap<>();
        searchParameter = searchParameter.replaceAll(" ", "%20");
        searchParameter = '"'+searchParameter+'"';
        try {
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
                    .header("Authorization", spotifyToken)
                    .build();
            String searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            for (SpotifyObject spotifyObject : spotifySearchResponseObject.getAllSpotifyResponseObjects()) {
                spotifyObjects.put(spotifyObject, spotifyObject.getName());
            }
        }
        catch(InterruptedException | IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        return spotifyObjects;
    }

    public ArrayList<Track> searchPopularTracksByArtist(String artistName, String spotifyToken) {
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
                    .addParameter("limit", "20")
                    .build();
            System.out.println(searchUri.toString());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyToken)
                    .build();
            searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            tracks = spotifySearchResponseObject.getTracksList().getTracks().stream()
                    .sorted(Comparator.comparing(Track::getPopularity))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        catch (URISyntaxException | InterruptedException | IOException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        tracks.trimToSize();
        return tracks;
    }

    public ArrayList<Album> searchAlbumByArtist(String artistName, String spotifyToken) {
        URI searchUri = null;
        String searchResponse = "";
        ArrayList<Album> albums = new ArrayList<>(3);
        String searchParameter = artistName.replaceAll(" ", "%20");
        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        searchParameter = "artist:" + '"'+searchParameter+'"' + " year:" +
                            (Integer.parseInt(currentYear) - 10) + "-" + currentYear;
        try {
            searchUri = new URIBuilder()
                    .setScheme(URI_SCHEME)
                    .setHost(URI_HOST)
                    .setPath("v1/search")
                    .addParameter("q", searchParameter)
                    .addParameter("type", "album")
                    .addParameter("limit", "3")
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .header("Authorization", spotifyToken)
                    .build();
            searchResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            SpotifySearchResponseObject spotifySearchResponseObject = gson.fromJson(searchResponse, SpotifySearchResponseObject.class);
            albums = spotifySearchResponseObject.getAlbumList().getAlbums();
        }
        catch (URISyntaxException | IOException | InterruptedException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        albums.trimToSize();
        return  albums;
    }
}
