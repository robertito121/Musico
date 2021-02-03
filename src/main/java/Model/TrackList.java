package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TrackList {

    @SerializedName(value = "items")
    private ArrayList<Track> tracks;

    public TrackList(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
