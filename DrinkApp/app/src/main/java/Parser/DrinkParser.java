package Parser;

import Model.DrinkModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by student on 10/26/15.
 */
public class DrinkParser {
    // Converts a JSON string to a RecipeModel
    static public DrinkModel JSONtoModel(String input){
        DrinkModel model = new DrinkModel();
        JSONObject json;
        try {
            json = (JSONObject) new JSONTokener(input).nextValue();
            JSONArray array = json.getJSONArray("results");
            JSONObject recipe = array.getJSONObject(0);

            Gson gson = new Gson();
            model = (DrinkModel)gson.fromJson(recipe.toString(), DrinkModel.class);

        }catch(JSONException exception){
            exception.getCause();
        }

        return model;
}
}
