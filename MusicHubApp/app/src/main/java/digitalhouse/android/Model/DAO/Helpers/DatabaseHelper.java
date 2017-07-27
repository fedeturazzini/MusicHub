package digitalhouse.android.Model.DAO.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import digitalhouse.android.Model.DAO.DAOSDatabase.DAOFavoritesDatabase;
import digitalhouse.android.Model.DAO.DAOSDatabase.DAOGenreDatabase;

/**
 * Created by gaston on 22/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "musicHubDataBase";
    public static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+
                DAOFavoritesDatabase.TABLE_NAME +" ("+
                DAOFavoritesDatabase.SONG_ID +" INTEGER PRIMARY KEY, " +
                DAOFavoritesDatabase.SONG_TITLE +" VARCHAR NOT NULL, " +
                DAOFavoritesDatabase.SONG_DURATION +" INTEGER NOT NULL, " +
                DAOFavoritesDatabase.SONG_ARTIST +" VARCHAR NOT NULL, " +
                DAOFavoritesDatabase.SONG_ALBUM_IMAGE +" VARCHAR NOT NULL, " +
                DAOFavoritesDatabase.SONG_RESOURCE +" VARCHAR NOT NULL)");

        db.execSQL("CREATE TABLE "+
                DAOGenreDatabase.TABLE_NAME +" ("+
                DAOGenreDatabase.COLUMN_ID +" INTEGER PRIMARY KEY, " +
                DAOGenreDatabase.COLUMN_NAME +" VARCHAR NOT NULL, " +
                DAOGenreDatabase.COLUMN_IMAGE +" VARCHAR NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
