package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mycrosoft.drinkapp.R;

/**
 * Created by Vue on 11/23/2015.
 */
public class WCIMStoredIngredListAdapter extends ArrayAdapter<String> {
    public WCIMStoredIngredListAdapter(Context context, ArrayList<String> stored_ingreds) {
        super(context, 0, stored_ingreds);
    }

    public WCIMStoredIngredListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.wcim_item, null);
        }

        String ingred = getItem(position);

        if (ingred != null) {
            TextView nameText = (TextView) v.findViewById(R.id.stored_ingred);
            if (nameText != null){
                nameText.setText(ingred);
            }
        }

        return v;
    }
}
