package Model;

import com.google.gson.annotations.SerializedName;

public class SpotifyObject {

    private String id;
    private String name;

    @SerializedName(value = "type", alternate = "album_type")
    private String type;

    private int popularity;

    public SpotifyObject(String id, String name, String type, int popularity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.popularity = popularity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

}
