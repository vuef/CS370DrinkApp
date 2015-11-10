package mycrosoft.drinkapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class WhatCanIMakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_what_can_imake);
    }
    /** Right now does nothing but will add ingredients to a txt file maybe**/
    public void add(View view){
        EditText editText = (EditText) findViewById(R.id.WCIMSearch);
        String message = editText.getText().toString();
    }
}
