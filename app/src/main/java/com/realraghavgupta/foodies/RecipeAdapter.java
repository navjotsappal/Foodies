package com.realraghavgupta.foodies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<RecipeItem> {
    private ArrayList<RecipeItem> recipeList;
    //Defining the structure of the recipe list
    public RecipeAdapter(Context context, int textViewResourceId,
                         ArrayList<RecipeItem> recipeList) {
        super(context, textViewResourceId, recipeList);
        this.recipeList = recipeList;
    }

    ImageLoader imageLoader = RecipeActivityController.getInstance().getImageLoader();

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.recipelist_item, null);
        }
        RecipeItem i = recipeList.get(position);
        if (i != null) {
            TextView title = v.findViewById(R.id.title);
            TextView rating = v.findViewById(R.id.rating);
            TextView ingredients = v.findViewById(R.id.genre);
            TextView timeins = v.findViewById(R.id.releaseYear);
            imageLoader = RecipeActivityController.getInstance().getImageLoader();
            NetworkImageView thumbNail = v.findViewById(R.id.thumbnail);
            thumbNail.setImageUrl(i.getThumbnailUrl(), imageLoader);
            title.setText(i.getTitle());
            rating.setText("Rating: " + String.valueOf(i.getRating()));
            String genreStr = "";
            for (String str : i.getingredients()) {
                genreStr += str + ", ";
            }
            genreStr += "\n ";
            ingredients.setText(genreStr);
            timeins.setText("Time: " + String.valueOf(i.gettimeinsec()));
        }
        return v;
    }
}
