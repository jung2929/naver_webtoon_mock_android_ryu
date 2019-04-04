package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListViewAdapter extends BaseAdapter {
    ArrayList<ListItem> list;
    Context context;
    LayoutInflater layoutInflater;
    public SearchListViewAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.list = new ArrayList<>();
        this.context = context;
    }

    public SearchListViewAdapter(Context context, ArrayList<ListItem> list) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
public void addItem(String title, int thumbnail){
        list.add(new ListItem(title,thumbnail));
}
    public void clear(){
        list.clear();
    }
    private class ViewHolder{
        String title; int thumbnail;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = list.get(position);
        ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.item_list, null);

            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title=item.getTvTitle();
        holder.thumbnail=item.getIvThumbnail();
        TextView tvTitle = convertView.findViewById(R.id.list_title);
        ImageView ivThumbnail = convertView.findViewById(R.id.list_thumbnail);
        tvTitle.setText(holder.title);
        ivThumbnail.setImageResource(holder.thumbnail);
        return convertView;
    }
}
