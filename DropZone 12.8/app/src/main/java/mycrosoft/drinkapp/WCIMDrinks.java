package mycrosoft.drinkapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Objects;
import java.util.Scanner;

import Define.AppDefines;
import adapters.DrinkListAdapter;
import adapters.IngredientListAdapter;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;

/**
 * Created by Fern on 12/6/2015.
 */
public class WCIMDrinks extends Activity{

    private ListView drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wcim_drinks);

        drinks = (ListView)findViewById(R.id.WCIMDrinks);

        Bundle extras = getIntent().getExtras();
        String ingredient = extras.getString("ingredient");

        APIClient.getDrinkProvider()
                .getDrinkByIngred(ingredient)
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
                        drinks.setAdapter(new DrinkListAdapter(WCIMDrinks.this, searchResultModel.drinks));
                    }
                });
        drinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent drinkIntent = new Intent(WCIMDrinks.this, DrinkActivity.class);
                drinkIntent.putExtra(AppDefines.DRINK_INTENT_KEY, Parcels.wrap((DrinkSearchItemModel) parent.getItemAtPosition(position)));
                startActivity(drinkIntent);
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

