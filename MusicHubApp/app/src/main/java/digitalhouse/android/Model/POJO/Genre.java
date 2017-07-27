package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gastonyelmini on 21/6/17.
 */

public class Genre {

    //Atributos
    @SerializedName("id")
    private Integer genreID;
    @SerializedName("name")
    private String genreName;
    @SerializedName("picture_medium")
    private String genrePictureResource;

    //Construtor
    public Genre(Integer genreID, String genreName, String genrePictureResource) {
        this.genreID = genreID;
        this.genreName = genreName;
        this.genrePictureResource = genrePictureResource;
    }

    //Getters
    public Integer getGenreID() {
        return genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getGenrePictureResource() {
        return genrePictureResource;
    }

    //setters
    public void setGenreID(Integer genreID) {
        this.genreID = genreID;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public void setGenrePictureResource(String genrePictureResource) {
        this.genrePictureResource = genrePictureResource;
    }

}
