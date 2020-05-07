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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

//Class for the tab for popular groceries
public class FragmentPopular extends Fragment {

    public static ArrayList<String> selectedItems = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        ListView groceryList = (ListView) view.findViewById(R.id.lv_groceryList);
        Button btnCompare = (Button) view.findViewById(R.id.btnCompare);
        groceryList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        //https://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<GroceryDetails> items = getGroceryItems();

        ListAdapter adapter = new ListAdapter(getContext(), items);
        groceryList.setAdapter(adapter);

        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                } else {
                    selectedItems.add(selectedItem);
                }
            }
        });

        //Navigate to comparing prices
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new ComparePrices()).commit();
            }
        });

        return view;
    }

    //Method to read the data from the JSON url
    public static List<GroceryDetails> getGroceryItems() {
        List<GroceryDetails> details = null;
        details = new ArrayList<GroceryDetails>();
        URL url;

        try {
            if (FragmentCatalog.CategoryId == -1) {
                url = new URL("http://35.190.169.87:9000/test/grocery");
            } else {
                url = new URL("http://35.190.169.87:9000/test/category/" + FragmentCatalog.CategoryId);
                FragmentCatalog.CategoryId = -1;
            }

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            urlConnection.disconnect();

            //https://stackoverflow.com/questions/26309773/deserialize-type-extending-abstract-class-in-gson
            Type targetClassType = new TypeToken<ArrayList<Grocery>>() {
            }.getType();
            ArrayList<Grocery> groceries = new Gson().fromJson(result.toString().substring(result.toString().indexOf("["), result.toString().indexOf("]") + 1), targetClassType);

            Iterator<Grocery> it = groceries.iterator();
            int i = 0, j = 0;

            if (FragmentCatalog.CategoryId == -1) {
                while (j < 10 && it.hasNext()) {
                    Grocery g = it.next();
                    GroceryDetails item = new GroceryDetails();
                    item.name = g.name;
                    item.ID = g.id;
                    item.url = g.picture;
                    details.add(item);
                    i++;
                    j++;
                }
            } else {
                while (it.hasNext()) {
                    Grocery g = it.next();
                    GroceryDetails item = new GroceryDetails();
                    item.name = g.name;
                    item.ID = g.id;
                    item.url = g.picture;
                    details.add(item);
                    i++;
                }
            }
        } catch (Exception ex) {
        } finally {

        }

        return details;
    }
}