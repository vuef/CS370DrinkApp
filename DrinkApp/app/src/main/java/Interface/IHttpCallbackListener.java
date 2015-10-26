package Interface;

/**
 * Created by student on 10/26/15.
 */
import Model.DrinkModel;

    // An interface to listen for the formatted (parsed) callback from a recipe API call over http
    public interface IHttpCallbackListener {
        public void onRecipeCallback(DrinkModel model);
    }
