package com.realraghavgupta.foodies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;
import java.util.logging.Logger;

//Class for inflating listview for the popular groceries
public class ListAdapter extends BaseAdapter {

    private Context contxt;
    List<GroceryDetails> items;
    int position, count;
    LayoutInflater inflater;
    private Logger logger = Logger.getLogger(ListAdapter.class.getName());

    public ListAdapter(Context contxt, List<GroceryDetails> items) {
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

    //inflating the view with the defined cell
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.rowlayout, parent, false);

        CheckedTextView ctv = (CheckedTextView) convertView.findViewById(R.id.txt_groceryitem);
        ctv.setText(items.get(position).name);
        if (FragmentPopular.selectedItems.contains(ctv.getText().toString()))
            ctv.setChecked(true);

        return convertView;
    }
}
