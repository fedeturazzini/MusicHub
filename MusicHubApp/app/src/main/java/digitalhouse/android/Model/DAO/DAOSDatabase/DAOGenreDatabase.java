package digitalhouse.android.Model.DAO.DAOSDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import digitalhouse.android.Model.DAO.Helpers.DatabaseHelper;
import digitalhouse.android.Model.POJO.Genre;

/**
 * Created by gaston on 04/07/17.
 */

public class DAOGenreDatabase extends DatabaseHelper {

    public static final String TABLE_NAME = "tableGenre";
    public static final String COLUMN_ID = "columnID";
    public static final String COLUMN_NAME = "genreName";
    public static final String COLUMN_IMAGE = "genreImage";

    public DAOGenreDatabase(Context context) {
        super(context);
    }

    public void addGenre(Genre genre) {

        if (!isGenreInDatabase(genre.getGenreID())) {

            SQLiteDatabase database = getWritableDatabase();

            ContentValues row = new ContentValues();
            row.put(COLUMN_ID, genre.getGenreID());
            row.put(COLUMN_NAME, genre.getGenreName());
            row.put(COLUMN_IMAGE, genre.getGenrePictureResource());

            database.insert(TABLE_NAME, null, row);

            database.close();

        }

    }

    public Boolean isGenreInDatabase(Integer id) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + id +"'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Boolean cursorResult = cursor.moveToNext();

        sqLiteDatabase.close();
        cursor.close();

        return cursorResult;

    }


}
