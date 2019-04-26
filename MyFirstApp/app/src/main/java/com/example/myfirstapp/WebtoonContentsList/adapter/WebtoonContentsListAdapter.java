package com.example.myfirstapp.WebtoonContentsList.Adapter;

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
import com.example.myfirstapp.common.Entities.WebtoonContentsData;

import java.net.URLEncoder;
import java.util.ArrayList;

/***************************
 *사용처
 *WebtoonContentsListActivity
 ***************************/
public class WebtoonContentsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WebtoonContentsData> webtoonContentsDataList;
    private LayoutInflater layoutInflater;
    public WebtoonContentsListAdapter(Context context, ArrayList<WebtoonContentsData> list) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.webtoonContentsDataList = list;
        this.context = context;
    }

    public void setDataList(ArrayList<WebtoonContentsData> list) {
        this.webtoonContentsDataList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (webtoonContentsDataList == null) {
            return 0;
        }
        return webtoonContentsDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return webtoonContentsDataList.get(position);
    }

    public static class ViewHolder {
        TextView title;
        TextView date;
        ImageView thumbnail;
        TextView starPoint;
        LinearLayout page;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WebtoonContentsData item = this.webtoonContentsDataList.get(position);
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


        if (item.getContentImg().equals("")) {//이미지가 없을 때
            holder.thumbnail.setImageResource(R.drawable.thumbnail_not_loaded);
        }
        else {
            try {
                String encodeURL = URLEncoder.encode(item.getContentImg(), "UTF-8");
                Glide.with(context)
                        .load(encodeURL)
                        .into(holder.thumbnail);
            }catch (Exception e){}
        }

        holder.title.setText(item.getContentName());
        holder.date.setText(item.getContentDate());
        holder.starPoint.setText(item.getContentRating());
        if (item.isRead()) {
            holder.page.setBackgroundResource(R.drawable.read_mark);
            holder.title.setTextColor(context.getResources().getColor(R.color.colorBlackfontexplain));
        } else {
            holder.page.setBackgroundResource(R.color.colorWhite);
            holder.title.setTextColor(context.getResources().getColor(R.color.colorBlackfont));
        }

        return convertView;
    }
}
