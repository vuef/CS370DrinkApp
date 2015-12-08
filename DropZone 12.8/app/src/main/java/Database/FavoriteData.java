package Database;

import android.provider.BaseColumns;

/**
 * Created by Vue on 11/22/2015.
 */
public class FavoriteData {
    public FavoriteData()
    {

    }

    public static abstract class FavoriteInfo implements BaseColumns
    {
        public static final String FAVORITES = "favorites";
        public static final String DATABASE_NAME = "user_favorites";
        public static final String TABLE_NAME = "favorites";
    }
}
