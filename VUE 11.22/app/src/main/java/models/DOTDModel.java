package models;

import com.google.gson.annotations.SerializedName;


/**
 * Created by MAtt on 11/18/2015.
 */

public class DOTDModel {
    @SerializedName("idDrink")
    public String drinkId;

    @SerializedName("strDrink")
    public String drinkName;

    @SerializedName("strDrinkThumb")
    public String thumbnail;
}
