package com.realraghavgupta.foodies;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends ArrayAdapter<IngredientsListItem> {
    public IngredientsListAdapter(Context context, int resource, List<IngredientsListItem> objects) {
        super(context, resource, objects);
    }//Initialising the adapter for the list

    static ArrayList<String> selectedStrings = new ArrayList<String>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.ingredients_list_item, parent, false); //Inflating the lingredients list view
        }
        final CheckBox ingredientTextView = convertView.findViewById(R.id.ingredientTextView);  //Adding checkboxes to the list
        final IngredientsListItem ingredient = getItem(position);
        ingredientTextView.setText(ingredient.getGroceryNames());
        ingredientTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    //Keeping a record of all the checked boxes and its values
                if (isChecked) {
                    selectedStrings.add(ingredientTextView.getText().toString());
                } else {
                    selectedStrings.remove(ingredientTextView.getText().toString());
                }
            }
        });
        return convertView;
    }
}