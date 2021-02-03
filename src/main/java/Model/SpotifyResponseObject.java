package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpotifyResponseObject {

    @SerializedName(value = "artists")
    private ArtistList artistsList;
    @SerializedName(value = "tracks")
    private TrackList tracksList;

    public SpotifyResponseObject(ArtistList artistsList, TrackList tracksList) {
        this.artistsList = artistsList;
        this.tracksList = tracksList;
    }

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
}

