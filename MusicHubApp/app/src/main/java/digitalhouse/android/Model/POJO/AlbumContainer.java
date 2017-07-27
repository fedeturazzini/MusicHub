package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaston on 23/06/17.
 */

public class AlbumContainer {

    @SerializedName("data")
    private List<Album> albumList;

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

}
