package Utility;

/**
 * Created by student on 10/26/15.
 */

    public class URLFormatUtility {
        final static private String BASE_URL = "http://www.thecocktaildb.com/api/json/v1/1/search.php?s=";

        static public String formatApiUrl(String params){
            String urlString = String.format(BASE_URL, params);
            return urlString;
        }
    }

