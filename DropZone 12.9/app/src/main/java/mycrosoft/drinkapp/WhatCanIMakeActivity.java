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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Database.WCIM;
import adapters.WCIMStoredIngredListAdapter;

public class WhatCanIMakeActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList <String> stored_ingredients = new ArrayList<String>();
    private WCIM DB = new WCIM(this);
    private ListView ingreds;
    private Button WCIMsearch;
    private EditText userInput;

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userInput = (EditText) findViewById(R.id.WCIMSearch);
        WCIMsearch = (Button) findViewById(R.id.WCIMButton);
        ingreds = (ListView) findViewById(R.id.WCIMStored);

        Cursor CR2 = DB.getInfo(DB);
        if (CR2 != null && CR2.getCount() > 0)
        {
            CR2.moveToFirst();
            String data = "";
            do {
                data += CR2.getString(0) + '\n';
            }while (CR2.moveToNext());
            Scanner scan = new Scanner(data);
            while(scan.hasNextLine()) {
                stored_ingredients.add(scan.nextLine());
            }
        }
        ingreds.setAdapter(new WCIMStoredIngredListAdapter(WhatCanIMakeActivity.this, stored_ingredients));

        WCIMsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _continue = false;
                String user_input = userInput.getText().toString();
                if (stored_ingredients.isEmpty()) {
                    stored_ingredients.add(user_input);
                    DB.insertIngredient(DB, user_input);
                    ingreds.setAdapter(new WCIMStoredIngredListAdapter(WhatCanIMakeActivity.this, stored_ingredients));
                }
                for (int i = 0; i < ingreds.getCount(); i++) {
                    if (ingreds.getItemAtPosition(i) != user_input) {
                        stored_ingredients.add(user_input);
                        DB.insertIngredient(DB, user_input);
                        ingreds.setAdapter(new WCIMStoredIngredListAdapter(WhatCanIMakeActivity.this, stored_ingredients));
                        break;
                    }
                }
            }
        });

        ingreds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent drinkIntent = new Intent(WhatCanIMakeActivity.this, WCIMDrinks.class);
                drinkIntent.putExtra("ingredient", ingreds.getItemAtPosition(position).toString());
                startActivity(drinkIntent);
            }
        });

        ingreds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DB.deleteIngredient(DB, ingreds.getItemAtPosition(position).toString());
                stored_ingredients.remove(position);
                ingreds.setAdapter(new WCIMStoredIngredListAdapter(WhatCanIMakeActivity.this, stored_ingredients));
                return true;
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
        int randomNum = rand.nextInt(((400 - 150) + 1)+ 150) * 2;
        while (randomNum < 225){
            randomNum = rand.nextInt(((400 - 150) + 1)+ 150) * 2;
        }
        final String drink = "14" + String.valueOf(randomNum);
        System.err.println(randomNum);
        if (id == R.id.nav_Search) {
            intent = new Intent(WhatCanIMakeActivity.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WhatCanIMakeActivity.this.startActivity(intent);
        } else if (id == R.id.nav_favorites) {
            intent = new Intent(WhatCanIMakeActivity.this, Favorites.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            WhatCanIMakeActivity.this.startActivity(intent);
        }else if (id == R.id.nav_random){
            intent = new Intent(WhatCanIMakeActivity.this, DOTDSingle.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("RECIPE_ID", drink);
            WhatCanIMakeActivity.this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
 /*
     APIClient.getDrinkProvider()
            .getDrinkByIngred(stored_ingredients.get(position))
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

                drinks.setAdapter(new DrinkListAdapter(WhatCanIMakeActivity.this, searchResultModel.drinks));
                //all_Drinks.add(searchResultModel);
            }

            else {

            }
        }
    });
    */