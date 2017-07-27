package digitalhouse.android.Model.DAO.DAOSDatabase;

import android.content.Context;

import digitalhouse.android.Model.DAO.Helpers.DatabaseHelper;

/**
 * Created by gaston on 04/07/17.
 */

public class DAOAlbumDatabase extends DatabaseHelper {

    //Constantes
    public static final String TABLE_NAME = "tableAlbum";
    public static final String COLUMN_ID = "columnId";
    public static final String COLUMN_TITLE = "columnTitle";
    public static final String COLUMN_LINK = "columnLink";
    public static final String COLUMN_COVER = "columnCover";
    public static final String COLUMN_COVER_MEDIUM = "columnMedium";
    public static final String COLUMN_COVER_XL = "columnCoverXl";
    public static final String COLUMN_ARTIST = "columnArtist";


    public DAOAlbumDatabase(Context context) {
        super(context);
    }
}
