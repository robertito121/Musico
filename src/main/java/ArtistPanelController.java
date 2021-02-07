import Model.Album;
import Model.Artist;
import Model.Track;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArtistPanelController implements Initializable {

    @FXML Label artistName;
    @FXML Label genres;
    @FXML ImageView artistImage;
    @FXML Label popularityRating;
    @FXML Label spotifyFollowers;
    @FXML ImageView trackImage1;
    @FXML ImageView trackImage2;
    @FXML ImageView trackImage3;
    @FXML ImageView trackImage4;
    @FXML ImageView trackImage5;
    @FXML ImageView trackImage6;
    @FXML ImageView trackImage7;
    @FXML ImageView trackImage8;
    @FXML ImageView trackImage9;
    @FXML Label trackName1;
    @FXML Label trackName2;
    @FXML Label trackName3;
    @FXML Label trackName4;
    @FXML Label trackName5;
    @FXML Label trackName6;
    @FXML Label trackName7;
    @FXML Label trackName8;
    @FXML Label trackName9;
    @FXML Label trackYear1;
    @FXML Label trackYear2;
    @FXML Label trackYear3;
    @FXML Label trackYear4;
    @FXML Label trackYear5;
    @FXML Label trackYear6;
    @FXML Label trackYear7;
    @FXML Label trackYear8;
    @FXML Label trackYear9;
    @FXML ImageView albumImage1;
    @FXML ImageView albumImage2;
    @FXML ImageView albumImage3;
    @FXML Label albumName1;
    @FXML Label albumName2;
    @FXML Label albumName3;
    @FXML Label albumYear1;
    @FXML Label albumYear2;
    @FXML Label albumYear3;
    private ArrayList<ImageView> trackImages;
    private ArrayList<Label> trackNames;
    private ArrayList<Label> trackYears;
    private ArrayList<ImageView> albumImages;
    private ArrayList<Label> albumNames;
    private ArrayList<Label> albumYears;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trackImages = new ArrayList<>();
        trackNames = new ArrayList<>();
        trackYears = new ArrayList<>();
        albumImages = new ArrayList<>();
        albumNames = new ArrayList<>();
        albumYears = new ArrayList<>();
        trackImages.add(trackImage1);
        trackImages.add(trackImage2);
        trackImages.add(trackImage3);
        trackImages.add(trackImage4);
        trackImages.add(trackImage5);
        trackImages.add(trackImage6);
        trackImages.add(trackImage7);
        trackImages.add(trackImage8);
        trackImages.add(trackImage9);
        trackNames.add(trackName1);
        trackNames.add(trackName2);
        trackNames.add(trackName3);
        trackNames.add(trackName4);
        trackNames.add(trackName5);
        trackNames.add(trackName6);
        trackNames.add(trackName7);
        trackNames.add(trackName8);
        trackNames.add(trackName9);
        trackYears.add(trackYear1);
        trackYears.add(trackYear2);
        trackYears.add(trackYear3);
        trackYears.add(trackYear4);
        trackYears.add(trackYear5);
        trackYears.add(trackYear6);
        trackYears.add(trackYear7);
        trackYears.add(trackYear8);
        trackYears.add(trackYear9);
        albumImages.add(albumImage1);
        albumImages.add(albumImage2);
        albumImages.add(albumImage3);
        albumNames.add(albumName1);
        albumNames.add(albumName2);
        albumNames.add(albumName3);
        albumYears.add(albumYear1);
        albumYears.add(albumYear2);
        albumYears.add(albumYear3);
    }

    public void populateArtistPanel(Artist artist) {
        artistName.setText(artist.getName());
        genres.setText(formatGenres(artist.getGenres()));
        artistImage.setImage(new Image(artist.getImages().get(0).getUrl()));
        popularityRating.setText(Integer.toString(artist.getPopularity()));
        spotifyFollowers.setText(Integer.toString(artist.getFollowers().getSpotifyFollowers()));
    }

    public void populateSongsAndAlbums(ArrayList<Track> tracks, ArrayList<Album> albums){
        for (int i = 0; i < trackImages.size(); i++) {
            trackImages.get(i).setImage(new Image(tracks.get(i).getAlbum().getImages().get(0).getUrl()));
            trackNames.get(i).setText(tracks.get(i).getName());
            trackYears.get(i).setText(tracks.get(i).getAlbum().getReleaseDate().substring(0,3));
        }
        for (int i = 0; i < albumImages.size(); i++) {
            albumImages.get(i).setImage(new Image(albums.get(i).getImages().get(0).getUrl()));
            albumNames.get(i).setText(albums.get(i).getName());
            albumYears.get(i).setText(albums.get(i).getReleaseDate().substring(0,3));
        }
    }

    private String formatGenres(ArrayList<String> genres) {
        String genresString = "";
        for (String genre : genres) {
            genresString = genresString + genre + ", ";
        }
        return genresString;
    }
}
