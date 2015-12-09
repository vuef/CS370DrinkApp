package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vue on 11/22/2015.
 */
public class WCIM extends SQLiteOpenHelper{
    public static final int database_version = 54;

    public String CREATE_QUERY = "CREATE TABLE " + WCIMData.TableInfo.TABLE_NAME + "(" + WCIMData.TableInfo.STORED_INGREDIENTS + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WCIMData.TableInfo.TABLE_NAME;

    public WCIM(Context context) {
        super(context, WCIMData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("WCIM ", "Database created ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("WCIM ", "Table created ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertIngredient(WCIM dop, String ingredient) {
        try {
            SQLiteDatabase SQ = dop.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(WCIMData.TableInfo.STORED_INGREDIENTS, ingredient);
            SQ.insertOrThrow(WCIMData.TableInfo.TABLE_NAME, null, cv);
            Log.d("WCIM", "One ingredient inserted." + ingredient);
            SQ.close();

        } catch (SQLiteException ex) {
            Log.e("This Broke", "WCIM: " + ex.getMessage());
        }
    }
    public Cursor getInfo(WCIM dop)
    {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {WCIMData.TableInfo.STORED_INGREDIENTS};
        Cursor CR = SQ.query(WCIMData.TableInfo.TABLE_NAME, columns, null, null,null, null, null);
        Log.d("WCIM: ", "Retrieving Info");
        return CR;
    }

    public void deleteIngredient(WCIM DOP, String ingredient)
    {
        String selection = WCIMData.TableInfo.STORED_INGREDIENTS + " LIKE ?";
        String args[] = {ingredient};
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        SQ.delete(WCIMData.TableInfo.TABLE_NAME, selection, args);
        Log.d("WCIM: ", "Deleting ingredient " + ingredient);
    }
}
