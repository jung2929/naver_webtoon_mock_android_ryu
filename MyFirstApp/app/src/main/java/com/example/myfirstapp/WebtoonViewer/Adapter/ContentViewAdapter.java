package com.example.myfirstapp.WebtoonViewer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.ViewHolder.ContentViewViewHolder;
import com.example.myfirstapp.WebtoonViewer.Entities.WebtoonContentViewData;

import java.net.URLEncoder;
import java.util.ArrayList;

public class ContentViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WebtoonContentViewData> list;
    private LayoutInflater layoutInflater;
    private String baseURL;

    public ContentViewAdapter(Context context, ArrayList<WebtoonContentViewData> list, String baseURL) {
        this.list = list;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.baseURL = baseURL;
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

    public void setData(ArrayList<WebtoonContentViewData> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WebtoonContentViewData item = list.get(position);
        ContentViewViewHolder holder;
        if (convertView == null) {
            convertView = new ImageView(context);
            holder = new ContentViewViewHolder();
            holder.content = (ImageView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ContentViewViewHolder) convertView.getTag();
        }
        try {
            String encodeURL = URLEncoder.encode(baseURL + item.getContentContent(), "UTF-8");
            Glide.with(context)
                    .load(encodeURL)
                    .into(holder.content);
        } catch (Exception e) {
        }
        return convertView;
    }
}
