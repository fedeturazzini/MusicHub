package digitalhouse.android.Model.POJO;

/**
 * Created by fedet on 22/6/2017.
 */

public class Artist {

    private Integer id;
    private String name;
    private String link;
    private String picture;
    private String picture_big;
    private String picture_medium;
    private String tracklist;

    // constructor
    public Artist() {
    }

    public Artist(Integer id, String name, String link, String picture, String picture_big, String picture_medium) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.picture_big = picture_big;
        this.picture_medium = picture_medium;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public String getTracklist() {
        return tracklist;
    }

    public String getLink() {
        return link;
    }

    public String getPicture_medium() {
        return picture_medium;
    }
}
