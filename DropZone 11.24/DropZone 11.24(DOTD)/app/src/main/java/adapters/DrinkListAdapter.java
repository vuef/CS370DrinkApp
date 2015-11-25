package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.DrinkSearchItemModel;
import mycrosoft.drinkapp.R;

/**
 * Created by Vue on 11/16/2015.
 */
public class DrinkListAdapter extends ArrayAdapter<DrinkSearchItemModel> {
    public DrinkListAdapter(Context context, ArrayList<DrinkSearchItemModel> recipes) {
        super(context, 0, recipes);
    }

    public DrinkListAdapter(Context context, int resource, List<DrinkSearchItemModel> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.drink_item, null);
        }

        DrinkSearchItemModel drink = getItem(position);

        if (drink != null) {
            TextView nameText = (TextView) v.findViewById(R.id.drinkName);
            ImageView recipeImage = (ImageView) v.findViewById(R.id.DrinkItemImage);
            if (nameText != null){
                nameText.setText(drink.drinkName);
            }
            Picasso.with(getContext()).load(drink.thumbnail).placeholder(R.drawable.ic_favorites).resize(200,200).into(recipeImage);


        }

        return v;
    }
}
