package mycrosoft.drinkapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import Define.AppDefines;
import adapters.IngredientListAdapter;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;

public class DrinkActivity extends Activity{
    private ImageView drinkImage;
    private ListView ingredientsList;
    private TextView drinkName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_drink);

        drinkImage = (ImageView)findViewById(R.id.drinkImage);
        ingredientsList = (ListView)findViewById(R.id.SingleDrinkIngredients);
        drinkName = (TextView)findViewById(R.id.name);
        // Unwrap the item from the Intent
      DrinkSearchItemModel drinkItem =
               (DrinkSearchItemModel) Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.DRINK_INTENT_KEY));
        APIClient.getDrinkProvider()
                .getDOTDRecipeById(drinkItem.drinkId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DrinkSearchResultModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 0;
                    }

                    @Override
                    public void onNext(DrinkSearchResultModel searchResultModel) {
                        drinkName.setText(searchResultModel.drinks.get(0).drinkName);
                        ingredientsList.setAdapter(new IngredientListAdapter(getBaseContext(), searchResultModel.drinks.get(0).getIngredients()));
                        Picasso.with(getBaseContext()).load(searchResultModel.drinks.get(0).thumbnail).placeholder(R.drawable.icon).resize(400, 400).into(drinkImage);
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

