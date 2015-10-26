package Service;
import java.net.URL;
import android.os.AsyncTask;
import Interface.IHttpCallbackListener;
import Model.DrinkModel;
import Parser.DrinkParser;


/**
 * Created by student on 10/26/15.
 */
public class DrinkDataManager extends AsyncTask<DrinkSearchRequestArgs, String, DrinkModel> {
        private IHttpCallbackListener callback;

        @Override
        protected DrinkModel doInBackground(Service.DrinkSearchRequestArgs... params) {
            // get parameter instance
            Service.DrinkSearchRequestArgs args = params[0];

            // set instances from arg values
            callback = args.getListener();
            String urlString = args.getUrl();

            HttpRequestManager hrm = new HttpRequestManager();
            String response = "";

            try {
                //convert string to URL
                URL url = new URL(urlString);
                //make Http call
                response = hrm.initiateHttpGet(url.toURI());
            }catch(Exception exception){

            }

            //handle the response
            DrinkModel model = DrinkParser.JSONtoModel(response);

            return model;
        }

        @Override
        protected void onPostExecute(DrinkModel drinkModel) {
            super.onPostExecute(drinkModel);
            // send the result back to the main UI thread
            callback.onRecipeCallback(drinkModel);
        }

    }

