package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fedet on 26/5/2017.
 */

public class Track {

    //atributos y serializes

    @SerializedName("id")
    private Integer songID;
    @SerializedName("title")
    private String songTitle;
    @SerializedName("duration")
    private Integer songDuration;
    @SerializedName("preview")
    private String songResource;

    private Artist artist;
    private Album album;
    private String trackArtistName;
    private String trackAlbumURL;

    //Constructor


    public Track() {
    }

    public Track(Integer songID, String songTitle, Integer songDuration, String songResource, Artist artist, Album album) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.songDuration = songDuration;
        this.songResource = songResource;
        this.artist = artist;
        this.album = album;
    }

    //seters
    public void setSongID(Integer songID) {
        this.songID = songID;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setSongDuration(Integer songDuration) {
        this.songDuration = songDuration;
    }

    public void setSongResource(String songResource) {
        this.songResource = songResource;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setTrackArtistName(String trackArtistName) {
        this.trackArtistName = trackArtistName;
    }

    public void setTrackAlbumURL(String trackAlbumURL) {
        this.trackAlbumURL = trackAlbumURL;
    }

    //getters

    public Integer getSongID() {
        return songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public Integer getSongDuration() {
        return songDuration;
    }

    public String getSongResource() {
        return songResource;
    }

    public Artist getArtist() {
        return artist;
    }

    public Album getAlbum() {
        return album;
    }

    public String getTrackAlbumURL() {
        return trackAlbumURL;
    }

    public String getTrackArtistName() {
        return trackArtistName;
    }
}
