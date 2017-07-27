package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fedet on 22/6/2017.
 */

public class Album {

    private Integer id;
    private String title;
    private String link;
    private String cover;
    private String cover_medium;
    private String cover_xl;
    private Artist artist;
    @SerializedName("tracklist")
    private String tracklistURL;

    public Album(Integer id, String title, String link, String cover, String cover_medium, String cover_xl, Artist artist, String tracklistURL) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.cover = cover;
        this.cover_medium = cover_medium;
        this.cover_xl = cover_xl;
        this.artist = artist;
        this.tracklistURL = tracklistURL;
    }

    // setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCover_medium(String cover_medium) {
        this.cover_medium = cover_medium;
    }

    public void setCover_xl(String cover_xl) {
        this.cover_xl = cover_xl;
    }

    public void setArtist(Artist artist) { this.artist = artist; }

    public void setTracklistURL(String tracklistURL) {
        this.tracklistURL = tracklistURL;
    }

    // getters

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getCover() {
        return cover;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public String getCover_xl() {
        return cover_xl;
    }

    public Artist getArtist() { return artist; }

    public String getTracklistURL() {
        return tracklistURL;
    }
}
