package models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Objects;

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

    @SerializedName("strInstructions")
    public String instructions;

    public ArrayList<String> getIngredients()
    {
        ArrayList<String> allIngreds = new ArrayList<String>();

        if (!Objects.equals(ingred1, ""))
            allIngreds.add(ingred1 + " " + amount1);

        if (!Objects.equals(ingred2, ""))
            allIngreds.add(ingred2 + " " + amount2);

        if (!Objects.equals(ingred3, ""))
            allIngreds.add(ingred3 + " " + amount3);

        if (!Objects.equals(ingred4, ""))
            allIngreds.add(ingred4 + " " + amount4);

        if (!Objects.equals(ingred5, ""))
            allIngreds.add(ingred5 + " " + amount5);

        if (!Objects.equals(ingred6, ""))
            allIngreds.add(ingred6 + " " + amount6);

        if (!Objects.equals(ingred7, ""))
            allIngreds.add(ingred7 + " " + amount7);

        if (!Objects.equals(ingred8, ""))
            allIngreds.add(ingred8 + " " + amount8);

        if (!Objects.equals(ingred9, ""))
            allIngreds.add(ingred9 + " " + amount9);

        if (!Objects.equals(ingred10, ""))
            allIngreds.add(ingred10 + " " + amount10);

        if (!Objects.equals(ingred11, ""))
            allIngreds.add(ingred11 + " " + amount11);

        if (!Objects.equals(ingred12, ""))
            allIngreds.add(ingred12 + " " + amount12);

        if (!Objects.equals(ingred13, ""))
            allIngreds.add(ingred13 + " " + amount13);

        if (!Objects.equals(ingred14, ""))
            allIngreds.add(ingred14 + " " + amount14);

        if (!Objects.equals(ingred15, ""))
            allIngreds.add(ingred15 + " " + amount15);
        return allIngreds;
    }

    // ALL INGREDIENTS
    @SerializedName("strIngredient1")
    public String ingred1;
    @SerializedName("strMeasure1")
    public String amount1;

    @SerializedName("strIngredient2")
    public String ingred2;
    @SerializedName("strMeasure2")
    public String amount2;

    @SerializedName("strIngredient3")
    public String ingred3;
    @SerializedName("strMeasure3")
    public String amount3;

    @SerializedName("strIngredient4")
    public String ingred4;
    @SerializedName("strMeasure4")
    public String amount4;

    @SerializedName("strIngredient5")
    public String ingred5;
    @SerializedName("strMeasure5")
    public String amount5;

    @SerializedName("strIngredient6")
    public String ingred6;
    @SerializedName("strMeasure6")
    public String amount6;

    @SerializedName("strIngredient7")
    public String ingred7;
    @SerializedName("strMeasure7")
    public String amount7;

    @SerializedName("strIngredient8")
    public String ingred8;
    @SerializedName("strMeasure8")
    public String amount8;

    @SerializedName("strIngredient9")
    public String ingred9;
    @SerializedName("strMeasure9")
    public String amount9;

    @SerializedName("strIngredient10")
    public String ingred10;
    @SerializedName("strMeasure10")
    public String amount10;

    @SerializedName("strIngredient11")
    public String ingred11;
    @SerializedName("strMeasure11")
    public String amount11;

    @SerializedName("strIngredient12")
    public String ingred12;
    @SerializedName("strMeasure12")
    public String amount12;

    @SerializedName("strIngredient13")
    public String ingred13;
    @SerializedName("strMeasure13")
    public String amount13;

    @SerializedName("strIngredient14")
    public String ingred14;
    @SerializedName("strMeasure14")
    public String amount14;

    @SerializedName("strIngredient15")
    public String ingred15;
    @SerializedName("strMeasure15")
    public String amount15;
}