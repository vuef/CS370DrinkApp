package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mycrosoft.drinkapp.R;

/**
 * Created by Vue on 11/16/2015.
 */
public class IngredientListAdapter extends ArrayAdapter<String> {
    public IngredientListAdapter(Context context, ArrayList<String> recipes) {
        super(context, 0, recipes);
    }

    public IngredientListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ingredient_item, null);
        }

        TextView ingredients = (TextView) v.findViewById(R.id.Ingredient);
        TextView amounts = (TextView) v.findViewById(R.id.Amount);

        String item = getItem(position);
        String ingredient = "";
        String amount = "";
        for (int i = 0; i < item.length(); i++) {
            char c = item.charAt(i);
            if (!Character.isDigit(c))
                ingredient += c;
            else {
                for (int y = i; y < item.length(); y++) {
                    char d = item.charAt(y);
                    amount += d;
                }
                break;
            }
        }
        amounts.setText(amount);
        ingredients.setText(ingredient);
        return v;
    }
}