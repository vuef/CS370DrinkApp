package mycrosoft.drinkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.parceler.Parcels;

import Define.AppDefines;
import adapters.RecipeListAdapter;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;


public class Main extends Activity {//AppCompatActivity {
    private ListView searchResultsList;
    private Button searchButton;
    private EditText searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchResultsList = (ListView)findViewById(R.id.Drinks);
        searchButton = (Button)findViewById(R.id.searchButton);
        searchInput = (EditText)findViewById(R.id.searchText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIClient.getRecipeProvider()
                        .getRecipesByIngredient(searchInput.getText().toString())
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
                                searchResultsList.setAdapter(new RecipeListAdapter(Main.this, searchResultModel.drinks));
                            }
                        });

            }
        });// An on-click listener for the items in the recipeListView

        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // When the recipe item is clicked, the following code is executed
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to contain the relevant recipe item data and to specify the activity to load
                Intent drinkIntent = new Intent(Main.this, DrinkActivity.class);
                // Wrap up the RecipeSearchItemModel using Parcels (arnother Gradle dependency)
                drinkIntent.putExtra(AppDefines.DRINK_INTENT_KEY, Parcels.wrap((DrinkSearchItemModel) parent.getItemAtPosition(position)));
                // Start the intended activity using the intent
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
/*
    // Opens the "What Can I Make?" view and displays "NOTHING". Called when the user clicks the "What Can I make?" button
    public void whatCanIMake(View view){
        Intent intent = new Intent(this,WhatCanIMakeActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, "NOTHING!");
        startActivity(intent);
    }
*/



