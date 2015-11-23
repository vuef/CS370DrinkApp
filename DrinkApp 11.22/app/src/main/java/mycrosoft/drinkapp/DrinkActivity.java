package mycrosoft.drinkapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import Define.AppDefines;
import models.DrinkModel;
import models.DrinkSearchItemModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;

public class DrinkActivity extends Activity{
    private ImageView drinkImage;
    private ListView ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_drink);

        drinkImage = (ImageView)findViewById(R.id.drinkImage);
        ingredientsList = (ListView)findViewById(R.id.SingleDrinkIngredients);
        // Unwrap the item from the Intent
      //  DrinkSearchItemModel drinkItem =
        //        (DrinkSearchItemModel) Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.DRINK_INTENT_KEY));

        setTitle("GG");//drinkItem.drinkName);

        // Using the id data from the unwrapped DrinkSearchItemModel, make a subsequent call to get the full recipe
        APIClient.getRecipeProvider()
                .getRecipeById("14071")//drinkItem.drinkId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DrinkModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 0;
                    }

                    @Override
                    public void onNext (DrinkModel drinkModel) {
                        // Handle the results
                        if (drinkModel != null) {
                            Picasso.with(getBaseContext()).load(drinkModel.drink.thumbnail).into(drinkImage);
                            // Create an inline adapter instead of using an inheriting class
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DrinkActivity.this, android.R.layout.simple_list_item_1, drinkModel.drink.ingredients);
                            ingredientsList.setAdapter(arrayAdapter);

                        } else {
                            // handle null
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

