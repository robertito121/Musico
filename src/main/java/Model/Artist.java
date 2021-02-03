package Model;

import java.util.ArrayList;

public class Artist extends SpotifyObject {

    private ArrayList<String> genres;
    private ArrayList<Image> images;

    public Artist(String id, String name, String type, int popularity, ArrayList<String> genres, ArrayList<Image> images) {
        super(id, name, popularity, type);
        this.genres = genres;
        this.images = images;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
