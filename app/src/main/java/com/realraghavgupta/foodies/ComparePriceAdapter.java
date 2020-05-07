package com.realraghavgupta.foodies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//Class for adapter for comparing prices for the selected grocery items
public class ComparePriceAdapter extends BaseAdapter {
    private Context contxt;
    public static String[] item;
    public static String[] groceryNames;
    public static String[] storeNames;
    public static String[] prices;
    public static int[] quantity;
    public static BigDecimal[] price1;
    public static BigDecimal[] price2;
    int position, count, itemIndex = 0;
    LayoutInflater inflater;

    //Constructor
    public ComparePriceAdapter(Context contxt, List<Price> items) {
        super();
        this.contxt = contxt;
        count = 0;
        item = new String[items.size() / 2];
        groceryNames = new String[item.length];
        storeNames = new String[item.length];
        prices = new String[item.length];
        quantity = new int[item.length];
        price2 = new BigDecimal[items.size() / 2];
        price1 = new BigDecimal[items.size() / 2];

        //Set the prices of each item for both the stores
        for (int index = 0; index < items.size(); index++) {
            if (!Arrays.asList(item).contains(items.get(index).itemId.name)) {
                item[itemIndex] = items.get(index).itemId.name;
                if (items.get(index).storeId.id == 1) {
                    price1[itemIndex] = items.get(index).price;
                    for (int j = index + 1; j < items.size(); j++) {
                        if (items.get(j).storeId.id == 2 && item[itemIndex].equals(items.get(j).itemId.name)) {
                            price2[itemIndex] = items.get(j).price;
                            break;
                        }
                    }
                    itemIndex++;
                } else {
                    price2[itemIndex] = items.get(index).price;
                    for (int j = index + 1; j < items.size(); j++) {
                        if (items.get(j).storeId.id == 1 && item[itemIndex].equals(items.get(j).itemId.name)) {
                            price1[itemIndex] = items.get(j).price;
                            break;
                        }
                    }
                    itemIndex++;
                }
            }
        }

        count = item.length;
        inflater = (LayoutInflater) this.contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return item[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Inflating the view with the cell defined
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_compare_grocery, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.txtgrocery);
        tv.setText(item[position]);

        RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.radioStore);
        final Spinner qnty = (Spinner) convertView.findViewById(R.id.quantity);
        final RadioButton rbSobeys = (RadioButton) convertView.findViewById(R.id.radioSobeys);
        rbSobeys.setText("$ " + price1[position].toString());
        final RadioButton rbWalmart = (RadioButton) convertView.findViewById(R.id.radioWalmart);
        rbWalmart.setText("$ " + price2[position].toString());

        if (price1[position].compareTo(price2[position]) == -1 || price1[position].compareTo(price2[position]) == 0) {
            rbSobeys.setChecked(true);
            storeNames[position] = "sobeys";
            prices[position] = rbSobeys.getText().toString();
        } else {
            rbWalmart.setChecked(true);
            storeNames[position] = "walmart";
            prices[position] = rbWalmart.getText().toString();
        }

        quantity[position] = 1;

        qnty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                quantity[position] = pos + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioSobeys) {
                    storeNames[position] = "sobeys";
                    prices[position] = rbSobeys.getText().toString();
                } else {
                    storeNames[position] = "walmart";
                    prices[position] = rbWalmart.getText().toString();
                }
            }
        });

        return convertView;
    }
}
