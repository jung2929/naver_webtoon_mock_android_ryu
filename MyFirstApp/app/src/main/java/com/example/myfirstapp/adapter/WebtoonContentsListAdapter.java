package com.example.myfirstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.entities.WebtoonContentsData;

import java.util.ArrayList;


public class WebtoonContentsListAdapter extends BaseAdapter{

    public static final int ITEM_COOKIE = 0 ;
    public static final int ITEM_AD = 1 ;
    public static final int ITEM_WEBTOON_LIST = 2 ;
    private static final int ITEM_NUMBER = 3 ;
    Context context;
    ArrayList<WebtoonContentsData> webtoonContentsData;
    LayoutInflater layoutInflater;
    public WebtoonContentsListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.webtoonContentsData = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_NUMBER;
    }

    @Override
    public int getItemViewType(int position) {
        return webtoonContentsData.get(position).getItemType();
    }

    @Override
    public int getCount() {
        return webtoonContentsData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return webtoonContentsData.get(position);
    }

    public static class ViewHolder {
        TextView title; TextView date; ImageView thumbnail;
        TextView starPoint; LinearLayout page;
    }

    public void addItem(WebtoonContentsData item){
        item.setItemType(ITEM_WEBTOON_LIST);
        webtoonContentsData.add(item);
    }
    public void addItem(int type){
        WebtoonContentsData item = new WebtoonContentsData();
        item.setItemType(type);
        webtoonContentsData.add(item);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WebtoonContentsData item = this.webtoonContentsData.get(position);
        int type = item.getItemType();
        switch (type) {
            case ITEM_WEBTOON_LIST:
                ViewHolder holder;
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_list_webtoon_contents, null);

                    holder = new ViewHolder();
                    holder.thumbnail = convertView.findViewById(R.id.webtoon_thumbnail);
                    holder.title = convertView.findViewById(R.id.webtoon_title);
                    holder.date = convertView.findViewById(R.id.webtoon_date);
                    holder.starPoint = convertView.findViewById(R.id.webtoon_starpoint);
                    holder.page = convertView.findViewById(R.id.list_page);
                    convertView.setTag(holder);

                } else {
                    holder = (ViewHolder) convertView.getTag();
                }


                if(item.getThumbnail()!=null)
                    Glide.with(context)
                            .load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg")
                            .into(holder.thumbnail);
                else
                    holder.thumbnail.setImageResource(R.drawable.thumbnail_not_loaded);
//                holder.thumbnail.setImageResource(item.getThumbnail());
                holder.title.setText(item.getTitle());
                holder.date.setText(item.getDate());
                holder.starPoint.setText(item.getStarPoint());
                if (item.isRead() == true) {
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
                    ImageView iv = convertView.findViewById(R.id.item_ad);
                    Glide.with(context).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(iv);
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
