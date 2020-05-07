package com.realraghavgupta.foodies;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeActivity extends Fragment {
    private ArrayList<RecipeItem> recipeList;
    private RecipeAdapter adapter;
    public static String recipe_id;
    ListView recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_recipe, container, false);
        recipeList = new ArrayList<>();
        adapter = new RecipeAdapter(this.getContext(), R.layout.recipelist_item, recipeList);
        recipe = root.findViewById(R.id.list);
        final String url = "https://api.yummly.com/v1/api/recipes?_app_id="+getString(R.string.YUMMLY_APP_ID)+"&_app_key=" + getString(R.string.YUMMLY_APP_KEY);    //get response from the api
        ArrayList<String> selectedStrings = IngredientsList.selectedIng;    //Get the list of selected ingredients
        String sel = "";
        for (String s : selectedStrings) {
            if (!s.isEmpty())
                sel += "&allowedIngredient[]=";
            s = s.toLowerCase();
            sel += s;
        }
        String urlWithArgument = url.concat(TextUtils.isEmpty(sel) ? "" : sel); //check if the selection is empty
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlWithArgument, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity().getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show(); //If the response is received, and no 606 is returned by API
                try {
                    recipeList.clear(); //clear the recipelist
                    org.json.JSONArray matches = response.getJSONArray("matches");  //parse the response from the API
                    for (int i = 0; i < matches.length(); i++) {
                        JSONObject obj = matches.getJSONObject(i);
                        String title = obj.getString("recipeName");
                        JSONObject objinu = obj.getJSONObject("imageUrlsBySize");
                        String url = objinu.getString("90");
                        int time = obj.getInt("totalTimeInSeconds");
                        String id = obj.getString("id");
                        Double rating = obj.getDouble("rating");
                        org.json.JSONArray ingreArry = obj.getJSONArray("ingredients");
                        java.util.ArrayList<String> ingre = new java.util.ArrayList<String>();
                        for (int j = 0; j < ingreArry.length(); j++) {
                            ingre.add((String) ingreArry.get(j));
                        }
                        recipeList.add(new RecipeItem(title, url, time, rating, id, ingre));
                    }
                    recipe.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Error retrieving data", Toast.LENGTH_SHORT).show(); //Display the error, in case retrival is not successful
            }
        }
        );
        RequestQueueSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        recipe.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                recipe_id = (String.valueOf(recipeList.get(i).getid()));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new RecipeViewActivity()).commit();   //When a recipe is selected, open the recipeview page which gives the details
            }
        });
        return root;
    }
}

