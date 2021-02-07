package Model;

import com.google.gson.annotations.SerializedName;

public class Followers {

    @SerializedName(value = "total")
    private int spotifyFollowers;

    public Followers(int spotifyFollowers) {
        this.spotifyFollowers = spotifyFollowers;
    }

    public int getSpotifyFollowers() {
        return spotifyFollowers;
    }

    public void setSpotifyFollowers(int spotifyFollowers) {
        this.spotifyFollowers = spotifyFollowers;
    }
}
