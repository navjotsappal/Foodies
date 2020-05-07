package com.realraghavgupta.foodies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//Adapter class for the catalog tab for groceries
public class CatalogAdapter extends BaseAdapter {
    private Context contxt;
    List<CatalogCategory> items;
    int position, count;
    LayoutInflater inflater;

    //Constructor
    public CatalogAdapter(Context contxt, List<CatalogCategory> items) {
        this.contxt = contxt;
        this.items = items;
        count = 0;

        inflater = (LayoutInflater) this.contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    } //need to change this

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Inflating with the the cell defined
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.catalog_layout_cell, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.txt_categoryitem);
        tv.setText(items.get(position).name);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv.setTextColor(Color.parseColor("#00aff0"));

        return convertView;
    }
}
