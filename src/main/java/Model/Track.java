package Model;

import java.util.ArrayList;

public class Track extends SpotifyObject {

    private boolean explicit;
    private ArrayList<Artist> artists;

    public Track(String id, String name, String type, int popularity, boolean explicit, ArrayList<Artist> artists) {
        super(id, name, popularity, type);
        this.explicit = explicit;
        this.artists = artists;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
}
