package digitalhouse.android.Controller;

import android.content.Context;

import digitalhouse.android.Model.DAO.DAOInternet.DAOAlbumInternet;
import digitalhouse.android.Model.POJO.AlbumContainer;
import digitalhouse.android.Util.HTTPConnectionManager;
import digitalhouse.android.Util.ResultListener;

/**
 * Created by gaston on 23/06/17.
 */

public class AlbumController {


    private static final Integer LIMIT = 10;
    private Integer offset = 0;
    private Boolean endPaging = false;



    public void obtainAlbum(Context context, final ResultListener<AlbumContainer> listener, Integer id) {

        if(HTTPConnectionManager.isNetworkingOnline(context)){
            DAOAlbumInternet albumInternet = new DAOAlbumInternet();
            albumInternet.getAlbumList(new ResultListener<AlbumContainer>() {
                @Override
                public void finish(AlbumContainer result) {
                    listener.finish(result);
                }
            }, id);
        }

    }

}

