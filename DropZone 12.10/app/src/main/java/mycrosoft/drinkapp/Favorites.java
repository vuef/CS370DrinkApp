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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Database.Favs;
import Define.AppDefines;
import adapters.DrinkListAdapter;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;


public class Favorites extends Activity
        implements NavigationView.OnNavigationItemSelectedListener{
    private ListView searchResultsList;
    private ArrayList <String> favorites = new ArrayList<String>();
    private Favs DB = new Favs(this);
    private ArrayList <DrinkSearchItemModel> _drinks = new ArrayList<DrinkSearchItemModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchResultsList = (ListView)findViewById(R.id.Drinks);

        Cursor CR2 = DB.getInfo(DB);
        if (CR2 != null && CR2.getCount() > 0) {
            CR2.moveToFirst();
            String data = "";
            do {
                data += CR2.getString(0) + '\n';
            } while (CR2.moveToNext());
            Scanner scan = new Scanner(data);
            do {
                favorites.add(scan.nextLine());
            } while (scan.hasNextLine());
        }

        for(String favorite : favorites) {
            APIClient.getDrinkProvider()
                    .getDOTDRecipeById(favorite)
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
                        public void onNext(DrinkSearchResultModel drinkModel) {
                            // Handle the results
                            if (drinkModel != null) {
                                _drinks.add(drinkModel.drinks.get(0));
                                searchResultsList.setAdapter(new DrinkListAdapter(Favorites.this, _drinks));
                            }
                        }
                    });
        }

        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // When the recipe item is clicked, the following code is executed
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to contain the relevant recipe item data and to specify the activity to load
                Intent drinkIntent = new Intent(Favorites.this, DrinkActivity.class);
                // Wrap up the RecipeSearchItemModel using Parcels (arnother Gradle dependency)
                drinkIntent.putExtra(AppDefines.DRINK_INTENT_KEY, Parcels.wrap((DrinkSearchItemModel) parent.getItemAtPosition(position)));
                // Start the intended activity using the intent
                startActivity(drinkIntent);
            }
        });

        searchResultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DrinkSearchItemModel del = (DrinkSearchItemModel) searchResultsList.getItemAtPosition(position);
                DB.deleteFavorite(DB, del.drinkId);
                _drinks.remove(position);
                searchResultsList.setAdapter(new DrinkListAdapter(Favorites.this, _drinks));
                return false;
            }

        });

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
        Random rand = new Random();
        int randomNum = rand.nextInt(((400 - 250) + 1)) * 2;
        while (randomNum < 250){
            System.err.println(randomNum);
            randomNum = rand.nextInt(((400 - 250) + 1)+ 150) * 2;
        }
        final String drink = "14" + String.valueOf(randomNum);
        System.err.println(randomNum);
        if (id == R.id.nav_Search) {
            intent = new Intent(Favorites.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Favorites.this.startActivity(intent);
        } else if (id == R.id.nav_WCIM) {
            intent = new Intent(Favorites.this, WhatCanIMakeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Favorites.this.startActivity(intent);
        }else if (id == R.id.nav_random){
            intent = new Intent(Favorites.this, DOTDSingle.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("RECIPE_ID", drink);
            Favorites.this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



