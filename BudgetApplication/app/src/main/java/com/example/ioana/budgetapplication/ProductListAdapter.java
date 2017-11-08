package com.example.ioana.budgetapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ioana on 08/11/2017.
 */

public class ProductListAdapter extends BaseAdapter {
    List<Product> products;
    LayoutInflater layoutInflater;

    public ProductListAdapter(List<Product> products, LayoutInflater layoutInflater) {
        this.products = products;
        this.layoutInflater = layoutInflater;
    }

    public ProductListAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.customproductlayoutlistitem,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.productNameTextView);
        imageView.setImageResource(products.get(position).getImagePath());
        textView.setText(products.get(position).getName());
        return convertView;
    }
}
