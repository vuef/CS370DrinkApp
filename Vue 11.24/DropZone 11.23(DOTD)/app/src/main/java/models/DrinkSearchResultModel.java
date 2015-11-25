package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vue on 11/9/2015.
 */
public class DrinkSearchResultModel {
    @SerializedName("drinks")
    public ArrayList<DrinkSearchItemModel> drinks;
}
