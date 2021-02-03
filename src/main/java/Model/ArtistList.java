package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ArtistList {

    @SerializedName(value = "items", alternate = "artists")
    private ArrayList<Artist> artists;

    public ArtistList(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
}
