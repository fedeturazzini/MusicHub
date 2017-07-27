package digitalhouse.android.Model.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gastonyelmini on 10/6/17.
 */

public class TrackContainer {

    @SerializedName("data")
    private List<Track> songsList;

    public void setSongsList(List<Track> songsList) {
        this.songsList = songsList;
    }

    public List<Track> getTrackList() {
        return songsList;
    }

}
