package com.realraghavgupta.foodies;

import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeViewActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootrview = inflater.inflate(R.layout.activity_recipeview, container, false);
        String recipe_id = RecipeActivity.recipe_id;
        final String url = "http://api.yummly.com/v1/api/recipe/" + recipe_id + "?_app_id="+getString(R.string.YUMMLY_APP_ID)+"&_app_key=" + getString(R.string.YUMMLY_APP_KEY);    //get response from the API for the details of the recipe
        final String[] inn = {"Ingredients: "};
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity().getApplicationContext(), "Success!",
                                Toast.LENGTH_SHORT).show();
                        try {
                            String rrname = response.getString("name"); //parse the API result for the required values
                            String totaltime = response.getString("totalTime");
                            String noser = response.getString("numberOfServings");
                            String rating = response.getString("rating");
                            JSONObject src = response.getJSONObject("source");
                            final String recipeurl = src.getString("sourceRecipeUrl");
                            JSONArray im = response.getJSONArray("images");
                            JSONObject imo = im.getJSONObject(0);
                            String imageurl = imo.getString("hostedMediumUrl");
                            JSONArray ing = response.getJSONArray("ingredientLines");
                            for (int x = 0; x < ing.length(); x++) {
                                inn[0] = inn[0].concat("\n" + ing.get(x).toString());
                            }
                            inn[0] = inn[0].concat("\n");
                            ImageLoader imageLoader;
                            imageLoader = RecipeActivityController.getInstance().getImageLoader();
                            NetworkImageView thumbNail = rootrview.findViewById(R.id.RecipeImage);
                            thumbNail.setImageUrl(imageurl, imageLoader);
                            TextView rname = rootrview.findViewById(R.id.RecipeName);
                            rname.setText(rrname);
                            TextView rtime = rootrview.findViewById(R.id.totaltime);
                            rtime.setText("Total time: " + totaltime);
                            TextView rserver = rootrview.findViewById(R.id.totalservings);
                            rserver.setText("Number of Servings: " + noser);
                            TextView rrating = rootrview.findViewById(R.id.ratings);
                            rrating.setText("Rating: " + rating);
                            TextView ring = rootrview.findViewById(R.id.ingredients);
                            ring.setText(inn[0]);
                            Button button = rootrview.findViewById(R.id.recipebutton);
                            button.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(getContext(), Uri.parse(recipeurl));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueueSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        return rootrview;
    }
}