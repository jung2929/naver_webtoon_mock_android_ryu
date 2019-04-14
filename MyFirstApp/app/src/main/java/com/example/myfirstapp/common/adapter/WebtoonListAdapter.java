package com.example.myfirstapp.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.common.entities.WebtoonData;

import java.util.ArrayList;
import java.util.List;

/***************************
 *사용처
 *MainActivity.MainWebtoonTabFragment, MainActivity.MyTabFragment,
 *SearchActivity
 ***************************/
public class WebtoonListAdapter extends BaseAdapter {
    public final static int TYPE_GRID=0;
    public final static int TYPE_LIST=1;

    private Context context;
    private ArrayList<WebtoonData> list;
    private LayoutInflater layoutInflater;
    private int webtoonNum, layout;
    private int viewType;
    public WebtoonListAdapter(Context context, ArrayList<WebtoonData> list,int layoutID, int viewType) {
        this.webtoonNum=0;
        this.list = list;
        this.layout = layoutID;
        this.layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.viewType = viewType;
        this.context = context;
    }
    public ArrayList<String> getWebtoonNames(){
        ArrayList<String> str=new ArrayList<>();
        for(int i=0; i<list.size(); i++)
            str.add(list.get(i).getComicName());
        return str;
    }
    public void clear(){
        list.clear();
    }
    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
        else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(WebtoonData item){
        switch (viewType) {
            case TYPE_GRID:
                if (list.size() == webtoonNum) {
                    list.add(item);
                } else {
                    list.set(webtoonNum, item);
                }
                while (list.size() % 3 != 0) {
                    WebtoonData tmp = new WebtoonData();
                    tmp.setNone(true);
                    list.add(tmp);
                }
                break;
            case TYPE_LIST:
                list.add(item);
                break;
        }
        webtoonNum++;
    }
    public static class ViewHolder {
        TextView title; TextView storyWriter; TextView painter;
        ImageView thumbnail; ImageView star; TextView starPoint;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WebtoonData item = list.get(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.thumbnail=convertView.findViewById(R.id.webtoon_thumbnail);
            holder.title=convertView.findViewById(R.id.webtoon_title);
            holder.starPoint=convertView.findViewById(R.id.webtoon_starpoint);
            holder.star = convertView.findViewById(R.id.webtoon_star);
            holder.storyWriter = convertView.findViewById(R.id.webtoon_story_writer);
            holder.painter = convertView.findViewById(R.id.webtoon_painter);
            if(viewType==TYPE_GRID) {
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(item.getThumbnail()!=null)
            Glide.with(context)
                    .load(item.getThumbnail())
                    .into(holder.thumbnail);
        else
            holder.thumbnail.setImageResource(R.drawable.thumbnail_not_loaded);

        holder.title.setText(item.getComicName());
        holder.starPoint.setText(item.getComicRating());
        holder.storyWriter.setText(item.getStoryWriter());

        //그림작가 없는 경우
        if(item.getPainter()==null) holder.painter.setText("");
        else holder.painter.setText("/"+item.getPainter());
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
