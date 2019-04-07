package com.example.myfirstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.enitites.WebtoonData;
import com.example.myfirstapp.enitites.WebtoonListData;

import java.util.ArrayList;

public class WebtoonListAdapter extends BaseAdapter {
    public final static int TYPE_GRID=0;
    public final static int TYPE_LIST=1;

    private ArrayList<WebtoonListData> list;
    private LayoutInflater layoutInflater;
    private int webtoonNum, layout;
    private int viewType;
    public WebtoonListAdapter(Context context, int layoutID, int viewType) {
        this.webtoonNum=0;
        this.list = new ArrayList<>();
        this.layout = layoutID;
        this.layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.viewType = viewType;
    }
    public ArrayList<String> getWebtoonNames(){
        ArrayList<String> str=new ArrayList<>();
        for(int i=0; i<list.size(); i++)
            str.add(list.get(i).getTitle());
        return str;
    }
    public void clear(){
        list.clear();
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
    public void addItem(WebtoonListData item){
        switch (viewType) {
            case TYPE_GRID:
                if (list.size() == webtoonNum) {
                    list.add(item);
                } else {
                    list.set(webtoonNum, item);
                }
                while (list.size() % 3 != 0) {
                    list.add(new WebtoonListData());
                }
                break;
            case TYPE_LIST:
                list.add(item);
                break;
        }
        webtoonNum++;
    }
    public static class ViewHolder {
        TextView title; TextView writer; ImageView thumbnail;
        ImageView star; TextView starPoint;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WebtoonListData item = list.get(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.thumbnail=convertView.findViewById(R.id.webtoon_thumbnail);
            holder.title=convertView.findViewById(R.id.webtoon_title);
            holder.starPoint=convertView.findViewById(R.id.webtoon_starpoint);
            holder.star = convertView.findViewById(R.id.webtoon_star);
            holder.writer = convertView.findViewById(R.id.webtoon_writer);
            if(viewType==TYPE_GRID) {
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.thumbnail.setImageResource(item.getThumbnail());
        holder.title.setText(item.getTitle());
        holder.starPoint.setText(item.getStarPoint());
        holder.writer.setText(item.getWriter());
        if(viewType==TYPE_GRID){
            if(item.isNone()){
                holder.star.setVisibility(View.INVISIBLE);
            }
            else {
                holder.star.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }
}
