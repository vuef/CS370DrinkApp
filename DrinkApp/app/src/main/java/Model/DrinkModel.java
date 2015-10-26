package Model;

/**
 * Created by student on 10/26/15.
 */
import com.google.gson.annotations.SerializedName;

public class DrinkModel {
    // An application model used to drive the SearchActivity view
    public class RecipeModel {

        @SerializedName("name")
        private String recipeName;

        @Serializedid("id")
        private String recipeID;

        @SerializedCuisine("cuisine")
        private String recipeCuisine;

        public String getRecipeName() {
            return recipeName;
        }

        public String getRecipeID() {
            return recipeID;
        }

        public String getRecipeCuisine() {
            return recipeCuisine ;
        }

    }
}
