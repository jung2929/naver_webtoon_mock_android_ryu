package com.example.myfirstapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GridThumnailAdapter extends BaseAdapter {
    private ArrayList<ItemGridThumbnail> list;
    LayoutInflater layoutInflater;
    int webtoonNum;
    public GridThumnailAdapter(Context context) {
        webtoonNum=0;
        this.list = new ArrayList<>();
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public ArrayList<String> getWebtoonNames(){
        ArrayList<String> str=new ArrayList<>();
        for(int i=0; i<list.size(); i++)
            str.add(list.get(i).getTitle());
        return str;
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
    public void addItem(int thumbnail, String title, String starPoint, String writer, Boolean isUpadate, Boolean isCuttoon){
        ItemGridThumbnail item = new ItemGridThumbnail(thumbnail, title, starPoint, writer,isUpadate, isCuttoon);
        if(list.size()==webtoonNum){
            list.add(item);
        }
        else{
            list.set(webtoonNum,item);
        }
        while(list.size()%3 != 0){
            list.add(new ItemGridThumbnail());
        }
        webtoonNum++;
    }
    public static class ViewHolder {
        TextView title; TextView writer; ImageView thumbnail;
        TextView starPoint;
        ImageView cuttoon; ImageView update; ImageView star;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemGridThumbnail item = list.get(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_grid_thumbnail, null);
            holder = new ViewHolder();
            holder.thumbnail=convertView.findViewById(R.id.item_grid_thumbnail);
            holder.title=convertView.findViewById(R.id.item_grid_title);
            holder.writer=convertView.findViewById(R.id.item_grid_writer);
            holder.starPoint=convertView.findViewById(R.id.item_grid_starpoint);
            holder.cuttoon = convertView.findViewById(R.id.item_grid_cuttoon);
            holder.update = convertView.findViewById(R.id.item_grid_update);
            holder.star = convertView.findViewById(R.id.item_grid_star);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.thumbnail.setImageResource(item.getThumbnail());
        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getWriter());
        holder.starPoint.setText(item.getStarPoint());
        if(item.isNone==true){
            holder.star.setVisibility(View.INVISIBLE);
        }
        else {
            holder.star.setVisibility(View.VISIBLE);
        }
        if(item.isCuttoon==true) holder.cuttoon.setVisibility(View.VISIBLE);
        else  holder.cuttoon.setVisibility(View.INVISIBLE);
        if(item.isUpadate==true) holder.update.setVisibility(View.VISIBLE);
        else  holder.update.setVisibility(View.INVISIBLE);
        return convertView;
    }
}
