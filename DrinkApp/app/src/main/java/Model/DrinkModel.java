package Model;

import android.net.Uri;

// An application model used to drive the SearchActivity view
public class DrinkModel {
    private String name;
    private String ingredients;
    private Uri drinkUri;
    private String id;
    private String thumb;
    private String image;

    public Uri getDrinkUri() {
        return drinkUri;
    }

    public void setRecipeUri(Uri recipeUri) {
        this.drinkUri = recipeUri;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID(String id){return id;}

    public void setID(String id){this.name = id;}

    public String getThumb(String thumb){return thumb;}

    public void setThumb(String thumb){this.name = thumb;}

    public String getImage(String image){return image;}

    public void setImage(String image){this.name = image;}


}
