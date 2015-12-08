package Database;

import android.provider.BaseColumns;

/**
 * Created by Vue on 11/22/2015.
 */
public class WCIMData {
    public WCIMData()
    {

    }

    public static abstract class TableInfo implements BaseColumns
    {
        public static final String STORED_INGREDIENTS = "ingredients";
        public static final String DATABASE_NAME = "user_ingredients";
        public static final String TABLE_NAME = "cabinet_info";
    }
}
