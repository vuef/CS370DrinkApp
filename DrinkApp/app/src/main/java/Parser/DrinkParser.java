package Parser;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Model.DrinkModel;

//
public class DrinkParser {
    // Converts a JSON string to a RecipeModel
    static public DrinkModel JSONtoModel(String input){
        DrinkModel model = new DrinkModel();
        JSONObject json;
        try {
            json = (JSONObject) new JSONTokener(input).nextValue();
            JSONArray array = json.getJSONArray("results");
            JSONObject recipe = array.getJSONObject(0);

            model.setName((String)recipe.get("name"));
            model.setIngredients((String) recipe.get("ingredients"));
            model.setRecipeUri(Uri.parse(((String) recipe.get("thumb"))));
            model.setID((String) recipe.get("id"));
            model.setThumb((String) recipe.get("thumb"));
            model.setImage((String) recipe.get("image"));
        }catch(JSONException exception){
            exception.getCause();
        }

        return model;
    }
}
