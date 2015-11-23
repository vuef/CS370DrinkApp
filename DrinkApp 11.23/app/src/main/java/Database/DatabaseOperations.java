package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vue on 11/22/2015.
 */
public class DatabaseOperations  extends SQLiteOpenHelper{
    public static final int database_version = 10;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_NAME + "(" + TableData.TableInfo.STORED_INGREDIENTS + " TEXT);";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created ");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created ");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertInfo(DatabaseOperations dop, String ingredient) {
        try {
            SQLiteDatabase db = dop.getWritableDatabase();
            ContentValues cv = new ContentValues();
            Log.d("Ingredient", ingredient);
            cv.put(TableData.TableInfo.STORED_INGREDIENTS, ingredient);
            db.insertOrThrow(TableData.TableInfo.TABLE_NAME, null, cv);
            Log.d("Database operations", "One row inserted.");

        } catch (SQLiteException ex) {
            Log.e("This Broke", "Here: " + ex.getMessage());
        }
    }
}
