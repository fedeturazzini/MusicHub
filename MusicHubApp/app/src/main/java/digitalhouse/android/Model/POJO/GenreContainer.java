package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gastonyelmini on 21/6/17.
 */

public class GenreContainer {

    @SerializedName("data")
    private List<Genre> genreList;

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }


}
