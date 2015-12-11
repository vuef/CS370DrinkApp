package mycrosoft.drinkapp;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Objects;
import java.util.Random;
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
public class WCIMDrinks extends Activity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ListView drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wcim_drinks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        if (id == R.id.nav_favorites) {
            intent = new Intent(WCIMDrinks.this, Favorites.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WCIMDrinks.this.startActivity(intent);

        } else if (id == R.id.nav_WCIM) {
            intent = new Intent(WCIMDrinks.this, WhatCanIMakeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WCIMDrinks.this.startActivity(intent);
        }else if (id == R.id.nav_random){
            intent = new Intent(WCIMDrinks.this, DOTDSingle.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("RECIPE_ID", drink);
            WCIMDrinks.this.startActivity(intent);
        }else if (id == R.id.nav_Search) {
            intent = new Intent(WCIMDrinks.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WCIMDrinks.this.startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

