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
import android.widget.Button;
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

import Database.Favs;
import Define.AppDefines;
import adapters.IngredientListAdapter;
import models.DOTDModel;
import models.DrinkSearchItemModel;
import models.DrinkSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;

public class DOTDSingle extends Activity
        implements NavigationView.OnNavigationItemSelectedListener{
    private ImageView drinkImage;
    private ListView ingredientsList;
    private TextView drinkName;
    private TextView instructions;
    private ImageButton favorite;
    private Favs favs = new Favs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_drink);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drinkImage = (ImageView)findViewById(R.id.drinkImage);
        ingredientsList = (ListView)findViewById(R.id.SingleDrinkIngredients);
        drinkName = (TextView)findViewById(R.id.name);
        favorite = (ImageButton) findViewById(R.id.favorite_button);
        instructions = (TextView) findViewById(R.id.instructions);

        Bundle extras = getIntent().getExtras();
        final String dotd = extras.getString("RECIPE_ID");


        Cursor CR2 = favs.getInfo(favs);
        if (CR2 != null && CR2.getCount() > 0) {
            CR2.moveToFirst();
            String data = "";
            do {
                data += CR2.getString(0) + '\n';
            } while (CR2.moveToNext());
            Scanner scan = new Scanner(data);
            do {
                if (Objects.equals(scan.nextLine(), dotd))
                    favorite.setSelected(true);
            } while (scan.hasNextLine());
        }
        APIClient.getDrinkProvider()
                .getDOTDRecipeById(dotd)
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
                        instructions.setText(searchResultModel.drinks.get(0).instructions);
                    }
                });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite.isSelected()) {
                    favorite.setSelected(false);
                    favs.deleteFavorite(favs, dotd);
                } else {
                    favorite.setSelected(true);
                    favs.insertFavorite(favs, dotd);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        Random rand = new Random();
        int randomNum = rand.nextInt(((400 - 150) + 1)+ 150) * 2;
        while (randomNum < 225){
            randomNum = rand.nextInt(((400 - 150) + 1)+ 150) * 2;
        }
        final String drink = "14" + String.valueOf(randomNum);
        System.err.println(randomNum);
        if (id == R.id.nav_favorites) {
            intent = new Intent(DOTDSingle.this, Favorites.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DOTDSingle.this.startActivity(intent);

        } else if (id == R.id.nav_WCIM) {
            intent = new Intent(DOTDSingle.this, WhatCanIMakeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DOTDSingle.this.startActivity(intent);
        }else if (id == R.id.nav_random){
            intent = new Intent(DOTDSingle.this, DOTDSingle.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("RECIPE_ID", drink);
            DOTDSingle.this.startActivity(intent);
        }else if (id == R.id.nav_Search) {
            intent = new Intent(DOTDSingle.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DOTDSingle.this.startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

