package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class WebtoonListAdapter extends BaseAdapter{

    public static final int ITEM_COOKIE = 0 ;
    public static final int ITEM_AD = 1 ;
    public static final int ITEM_WEBTOON_LIST = 2 ;
    private static final int ITEM_NUMBER = 3 ;
    Context context;
    ArrayList<ListItem> listItems;
    LayoutInflater layoutInflater;
    public WebtoonListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.listItems = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_NUMBER;
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).getItemtype();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    public static class ViewHolder {
        TextView title; TextView date; ImageView thumbnail;
        TextView starPoint; LinearLayout page;
    }

    public void addItem(int ivThumbnail, String tvTitle, String tvStarPoint, String tvDate, boolean read){
        ListItem item = new ListItem(ivThumbnail, tvTitle,tvStarPoint,tvDate,read);
        item.setItemtype(ITEM_WEBTOON_LIST);
        listItems.add(item);
    }
    public void addItem(int type){
        ListItem item = new ListItem();
        item.setItemtype(type);
        listItems.add(item);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItem listItem = listItems.get(position);
        int type = listItem.getItemtype();
        switch (type) {
            case ITEM_WEBTOON_LIST:
                ViewHolder holder;
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_list, null);

                    holder = new ViewHolder();
                    holder.thumbnail = convertView.findViewById(R.id.list_thumbnail);
                    holder.title = convertView.findViewById(R.id.list_title);
                    holder.date = convertView.findViewById(R.id.list_date);
                    holder.starPoint = convertView.findViewById(R.id.list_star_point);
                    holder.page = convertView.findViewById(R.id.list_page);
                    convertView.setTag(holder);

                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.thumbnail.setImageResource(listItem.getIvThumbnail());
                holder.title.setText(listItem.getTvTitle());
                holder.date.setText(listItem.getTvDate());
                holder.starPoint.setText(listItem.getTvStarPoint());
                if (listItem.isRead() == true) {
                    holder.page.setBackgroundResource(R.drawable.read_mark);
                    holder.title.setTextColor(context.getResources().getColor(R.color.blackfontexplain));
                }
                else{
                    holder.page.setBackgroundResource(R.color.colorWhite);
                    holder.title.setTextColor(context.getResources().getColor(R.color.blackfont));
                }
                break;
            case ITEM_AD:
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_list_ad, null);
                }
                break;
            case ITEM_COOKIE:
                if(convertView==null){
                    convertView = layoutInflater.inflate(R.layout.item_list_cookie, null);
                }
                break;
        }
        return convertView;
    }
}
