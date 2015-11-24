package mycrosoft.drinkapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Scanner;

import Database.DatabaseOperations;
import Define.AppDefines;
import adapters.DrinkListAdapter;
import adapters.WCIMStoredIngredListAdapter;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;

public class WhatCanIMakeActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener{

    Context cxt = this;
    ArrayList <DrinkSearchResultModel> all_Drinks= new ArrayList<DrinkSearchResultModel>();
    ArrayList <DrinkSearchItemModel> WCIM = new ArrayList<DrinkSearchItemModel>();
    ArrayList <String> stored_ingredients = new ArrayList<String>();
    DatabaseOperations DB = new DatabaseOperations(cxt);
    ListView drinks;
    ListView ingreds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_can_imake);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drinks = (ListView) findViewById(R.id.WCIMDrinks);
        ingreds = (ListView) findViewById(R.id.WCIMStored);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final EditText input = (EditText) findViewById(R.id.WCIMSearch);
        Button button = (Button) findViewById(R.id.WCIMButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_input = input.getText().toString();
                DB.insertInfo(DB, user_input);
                stored_ingredients.add(user_input);
                Toast.makeText(getBaseContext(), "Added ingredient: " + user_input, Toast.LENGTH_LONG).show();

                Cursor CR2 = DB.getInfo(DB);
                CR2.moveToFirst();
                String data = "";
               do {
                   data += CR2.getString(0) + '\n';
                }while (CR2.moveToNext());
                Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
                Scanner scan = new Scanner(data);
                while(scan.hasNextLine()) {
                    stored_ingredients.add(scan.nextLine());
                }

                ///**/}

                //for (String i : stored_ingredients) {
                    APIClient.getDrinkProvider()
                            .getDrinkByIngred(stored_ingredients.get(0))
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

                                    if (searchResultModel != null)
                                    {
                                        ingreds.setAdapter(new WCIMStoredIngredListAdapter(WhatCanIMakeActivity.this, stored_ingredients));
                                        drinks.setAdapter(new DrinkListAdapter(WhatCanIMakeActivity.this, searchResultModel.drinks));
                                        //all_Drinks.add(searchResultModel);
                                    }

                                    else {

                                    }
                                }
                            });
                //}

            /*
                for (int x = 0; x < all_Drinks.size(); x++)
                {
                        for (DrinkSearchItemModel item : all_Drinks.get(x).drinks)
                            WCIM.add(item);
                }
               */
            }
        });
        drinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // When the recipe item is clicked, the following code is executed
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to contain the relevant recipe item data and to specify the activity to load
                Intent drinkIntent = new Intent(WhatCanIMakeActivity.this, DrinkActivity.class);
                // Wrap up the RecipeSearchItemModel using Parcels (arnother Gradle dependency)
                drinkIntent.putExtra(AppDefines.DRINK_INTENT_KEY, Parcels.wrap((DrinkSearchItemModel) parent.getItemAtPosition(position)));
                // Start the intended activity using the intent
                startActivity(drinkIntent);
            }
        });
        //drinks.setAdapter(new DrinkListAdapter(WhatCanIMakeActivity.this, WCIM));
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_Search) {
            intent = new Intent(WhatCanIMakeActivity.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WhatCanIMakeActivity.this.startActivity(intent);
        } else if (id == R.id.nav_favorites) {
            intent = new Intent(WhatCanIMakeActivity.this, Favorites.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WhatCanIMakeActivity.this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
 /*     ArrayList <String> MockData = new ArrayList<String>();
 MockData.add("Vodka");
        MockData.add("Whiskey");
        MockData.add("Lime");
 for (String i: MockData) {
                    Log.d("Data", i);

                    APIClient.getDrinkProvider()
                            .getDrinkByIngred(i)
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
                                    drinks.setAdapter(new DrinkListAdapter(WhatCanIMakeActivity.this, searchResultModel.drinks));
                                    all_Drinks.add(searchResultModel);
                                }
                            });
                }*/