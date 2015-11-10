package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vue on 11/9/2015.
 */
public class DrinkData {
    @SerializedName("strDrink")
    public String drinkName;

    @SerializedName("strDrinkThumb")
    public String thumbnail;

    @SerializedName("strInstructions")
    public String instructions;

    @SerializedName("strIngredient1")
    public ArrayList<String> ingredients;
}
