package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Album extends SpotifyObject {

    private ArrayList<Artist> artists;
    private ArrayList<Image> images;
    private TrackList tracks;

    @SerializedName(value = "release_date")
    private String releaseDate;

    @SerializedName(value = "total_tracks")
    private int totalTracks;

    public Album(String id, String name, String type,
                 int popularity, ArrayList<Artist> artists, ArrayList<Image> images,
                 String releaseDate, int totalTracks, TrackList tracks) {
        super(id, name, type, popularity);
        this.artists = artists;
        this.images = images;
        this.releaseDate = releaseDate;
        this.totalTracks = totalTracks;
        this.tracks = tracks;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }
}
