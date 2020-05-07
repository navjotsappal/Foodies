package com.realraghavgupta.foodies;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Class for the fragment for catalog of the recipes based on categories
public class FragmentCatalog extends Fragment {
    ArrayList<String> selectedItems = new ArrayList<String>();
    public static int CategoryId = -1;
    public static String categoryName = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        ListView groceryList = (ListView) view.findViewById(R.id.lv_groceryListCatalog);
        groceryList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        //https://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final List<CatalogCategory> items = getGroceryItems();

        CatalogAdapter adapter = new CatalogAdapter(getContext(), items);
        groceryList.setAdapter(adapter);

        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryId = position + 1;
                categoryName = items.get(position).name;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new Ordering()).commit();
            }
        });

        return view;
    }

    //Method to read data form the JSON url
    public static List<CatalogCategory> getGroceryItems() {

        List<CatalogCategory> details = null;
        details = new ArrayList<CatalogCategory>();

        try {
            URL url = new URL("http://35.190.169.87:9000/test/category");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            urlConnection.disconnect();

            //https://stackoverflow.com/questions/26309773/deserialize-type-extending-abstract-class-in-gson
            Type targetClassType = new TypeToken<ArrayList<CatalogCategory>>() {
            }.getType();
            ArrayList<CatalogCategory> catalogs = new Gson().fromJson(result.toString(), targetClassType);

            Iterator<CatalogCategory> it = catalogs.iterator();
            int i = 0;

            while (it.hasNext()) {
                CatalogCategory g = it.next();
                CatalogCategory item = new CatalogCategory();
                item.name = g.name;
                item.id = g.id;
                item.description = g.description;
                details.add(item);
                i++;
            }
        } catch (Exception ex) {
        } finally {
        }

        return details;
    }
}
