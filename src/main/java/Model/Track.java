package Model;

import java.util.ArrayList;

public class Track extends SpotifyObject {

    private boolean explicit;
    private ArrayList<Artist> artists;
    private Album album;
    private int popularity;

    public Track(String id, String name, String type, int popularity, boolean explicit, ArrayList<Artist> artists, Album album) {
        super(id, name, type);
        this.explicit = explicit;
        this.artists = artists;
        this.album = album;
        this.popularity = popularity;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
