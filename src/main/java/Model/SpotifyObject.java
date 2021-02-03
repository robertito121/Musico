package Model;

public class SpotifyObject {

    private String id;
    private String name;
    private int popularity;

    public SpotifyObject(String id, String name, int popularity, String type) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
        this.type = type;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


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
}
