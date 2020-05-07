package com.realraghavgupta.foodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import static android.content.Context.MODE_PRIVATE;

//Class to compare prices at both the stores for the selected items
public class ComparePrices extends Fragment {

    static ArrayList<String> selectedItems = FragmentPopular.selectedItems;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_compare_prices, container, false);

        ListView priceList = (ListView) view.findViewById(R.id.lv_prices);

        // Add a header to the ListView
        LayoutInflater inflater1 = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.pricelist_header, priceList, false);
        priceList.addHeaderView(header);

        Button btnAdd = (Button) view.findViewById(R.id.btnAdd);

        //https://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Price> items = getPrices();

        ComparePriceAdapter adapter = new ComparePriceAdapter(getContext(), items);
        priceList.setAdapter(adapter);

        //Check if the user is signed in, else place order for the selected items
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db_ref2 = FirebaseDatabase.getInstance().getReference("Orders");
                String userId = db_ref2.push().getKey();
                SharedPreferences prefs = getContext().getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);
                String user_email = prefs.getString("userEmail", "No email defined");

                if (user_email.equals("No email defined")) {
                    startActivity(new Intent(getContext(), sign_in.class));
                    FragmentPopular.selectedItems.clear();
                    Toast.makeText(getContext(), "Please sign in first to add items to cart", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < ComparePriceAdapter.storeNames.length; i++) {
                        userId = db_ref2.push().getKey();
                        UserOrders userOrders = new UserOrders(user_email, ComparePriceAdapter.item[i], ComparePriceAdapter.storeNames[i], ComparePriceAdapter.prices[i], ComparePriceAdapter.quantity[i]);
                        db_ref2.child(userId).setValue(userOrders);
                    }
                    Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                    FragmentPopular.selectedItems.clear();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new Ordering()).commit();
                }
            }
        });

        return view;
    }

    //Read prices from the JSON url
    public static List<Price> getPrices() {

        List<Price> details = null;
        details = new ArrayList<Price>();

        try {
            URL url = new URL("http://35.190.169.87:9000/test/price");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            urlConnection.disconnect();

            //https://stackoverflow.com/questions/26309773/deserialize-type-extending-abstract-class-in-gson
            Type targetClassType = new TypeToken<ArrayList<Price>>() {
            }.getType();
            ArrayList<Price> priceList = new Gson().fromJson(result.toString(), targetClassType);

            Iterator<Price> it = priceList.iterator();
            int i = 0;

            while (it.hasNext()) {
                Price p = it.next();
                Price item = new Price();
                ArrayList<String> selected = selectedItems;
                if (selectedItems.contains(p.itemId.name)) {
                    item.id = p.id;
                    item.itemId = p.itemId;
                    item.note = p.note;
                    item.price = p.price;
                    item.storeId = p.storeId;
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