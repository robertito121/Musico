package Model;

import java.util.ArrayList;

public class Artist extends SpotifyObject {

    private ArrayList<String> genres;
    private ArrayList<Image> images;
    private Followers followers;
    private int popularity;

    public Artist(String id, String name, String type, int popularity, ArrayList<String> genres, ArrayList<Image> images, Followers followers) {
        super(id, name, type);
        this.genres = genres;
        this.images = images;
        this.followers = followers;
        this.popularity = popularity;
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

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
