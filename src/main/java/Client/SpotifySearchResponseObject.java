package Client;

import Model.AlbumList;
import Model.ArtistList;
import Model.SpotifyObject;
import Model.TrackList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Spotify response object
 */
public class SpotifySearchResponseObject {

    @SerializedName(value = "artists")
    private ArtistList artistsList;

    @SerializedName(value = "tracks")
    private TrackList tracksList;

    @SerializedName(value = "albums")
    private AlbumList albumList;

    public SpotifySearchResponseObject(ArtistList artistsList, TrackList tracksList, AlbumList albumList) {
        this.artistsList = artistsList;
        this.tracksList = tracksList;
        this.albumList = albumList;
    }

    /**
     * Returns all response objects
     * @return ArrayList<SpotifyObject>
     */
    public ArrayList<SpotifyObject> getAllSpotifyResponseObjects() {
        ArrayList<SpotifyObject> spotifyObjects = new ArrayList<>(artistsList.getArtists());
        spotifyObjects.addAll(tracksList.getTracks());
        return spotifyObjects;
    }

    public ArtistList getArtistsList() {
        return artistsList;
    }

    public void setArtistsList(ArtistList artistsList) {
        this.artistsList = artistsList;
    }

    public TrackList getTracksList() {
        return tracksList;
    }

    public void setTracksList(TrackList tracksList) {
        this.tracksList = tracksList;
    }

    public AlbumList getAlbumList() {
        return albumList;
    }

    public void setAlbumList(AlbumList albumList) {
        this.albumList = albumList;
    }
}

