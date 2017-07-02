package com.example.owner.myliteraryapplication;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mark on 22/08/2016.
 */
public class CustomAdapter extends BaseAdapter {

    String items[];
    int[] icons;
    LayoutInflater mInflater;

    // I want the text as well as the icons
    public CustomAdapter(Context context, String[] items, int[] icons) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView ==null)
        {
            convertView = mInflater.inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(items[position]);
        holder.iv.setImageResource(icons[position]);
        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv;
    }
}