package models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Vue on 11/9/2015.
 */
@Parcel
public class DrinkSearchItemModel {
    @SerializedName("idDrink")
    public String drinkId;

    @SerializedName("strDrink")
    public String drinkName;

    @SerializedName("strDrinkThumb")
    public String thumbnail;
}