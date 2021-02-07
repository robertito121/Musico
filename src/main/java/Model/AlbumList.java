package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class AlbumList {

    @SerializedName(value = "items", alternate = "albums")
    private ArrayList<Album> albums;

    public AlbumList(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }
}
