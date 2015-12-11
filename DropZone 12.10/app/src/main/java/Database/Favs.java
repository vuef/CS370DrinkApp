package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Vue on 11/22/2015.
 */
public class Favs extends SQLiteOpenHelper{
    public static final int database_version = 51;

    public String CREATE_QUERY = "CREATE TABLE " + FavoriteData.FavoriteInfo.TABLE_NAME + "(" + FavoriteData.FavoriteInfo.FAVORITES + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoriteData.FavoriteInfo.TABLE_NAME;

    public Favs(Context context) {
        super(context, FavoriteData.FavoriteInfo.DATABASE_NAME, null, database_version);
        Log.d("Favorites", "Database created ");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Favorites", "Table created ");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertFavorite(Favs dop, String drinkID) {
        try {
            SQLiteDatabase SQ = dop.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FavoriteData.FavoriteInfo.FAVORITES, drinkID);
            SQ.insertOrThrow(FavoriteData.FavoriteInfo.TABLE_NAME, null, cv);
            Log.d("Favorites", "One drink inserted:" + drinkID);
            SQ.close();

        } catch (SQLiteException ex) {
            Log.e("This Broke in Favorites", "Here: " + ex.getMessage());
        }
    }

    public Cursor getInfo(Favs dop)
    {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {FavoriteData.FavoriteInfo.FAVORITES};
        Cursor CR = SQ.query(FavoriteData.FavoriteInfo.TABLE_NAME, columns, null, null, null, null, null);
        Log.e("Favorites: ", "Retrieving Info");
        return CR;
    }

    public void deleteFavorite(Favs DOP, String drinkID)
    {
        String selection = FavoriteData.FavoriteInfo.FAVORITES + " LIKE ?";
        String args[] = {drinkID};
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        SQ.delete(FavoriteData.FavoriteInfo.TABLE_NAME, selection, args);
        Log.e("Favorites: ", "Deleteing " + drinkID);
    }
}
