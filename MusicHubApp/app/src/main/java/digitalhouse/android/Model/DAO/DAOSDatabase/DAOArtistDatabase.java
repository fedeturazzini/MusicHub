package digitalhouse.android.Model.DAO.DAOSDatabase;

import android.content.Context;

import digitalhouse.android.Model.DAO.Helpers.DatabaseHelper;

/**
 * Created by gaston on 04/07/17.
 */

public class DAOArtistDatabase extends DatabaseHelper {

    public static final String TABLE_NAME = "artistTable";
    public static final String COLUMN_ID = "columnID";
    public static final String COLUMN_NAME = "columnName";
    public static final String COLUMN_LINK = "columnLink";
    public static final String COLUMN_PICTURE = "columnPicture";
    public static final String COLUMN_PICTURE_BIG = "columnPictureBig";
    public static final String COLUMN_PICTURE_MEDIUM = "columnPicureMedium";
    public static final String COLUMN_TRACKLIST_URL = "columnTracklistURL";

    public DAOArtistDatabase(Context context) {
        super(context);
    }

}






