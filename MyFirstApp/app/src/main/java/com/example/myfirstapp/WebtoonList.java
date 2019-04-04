package com.example.myfirstapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebtoonList extends AppCompatActivity {
    Intent intentGet;
    TextView tvWebtoonName;
    String webtoonName;

    ListView listView;
    WebtoonListAdapter adapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_list);

        //이전의 activity에서 얻어와 웹툰 제목 설정
        intentGet = getIntent();
        webtoonName = intentGet.getExtras().getString("webtoonName");
        tvWebtoonName = findViewById(R.id.webtoonName);
        tvWebtoonName.setText(webtoonName);

        int thumbnail=R.drawable.thumbnail_not_loaded2;
        if(webtoonName.equals("복학왕")) thumbnail = R.drawable.thumbnail_king_1;
        else if(webtoonName.equals("소녀의 세계")) thumbnail = R.drawable.thumbnail_1_1;
        else if(webtoonName.equals("데드라이프")) thumbnail = R.drawable.thumbnail_dead_life_1;

        adapter = new WebtoonListAdapter(this);
        adapter.addItem(WebtoonListAdapter.ITEM_AD);
        adapter.addItem(WebtoonListAdapter.ITEM_COOKIE);

        for(int i=1; i<20; i++){
            adapter.addItem(thumbnail, webtoonName+" "+i+"화", "9.99", "19.03."+i, false);
        }
        listView = findViewById(R.id.listView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem l = (ListItem)listView.getItemAtPosition(position);
                switch (l.getItemtype()){
                    case WebtoonListAdapter.ITEM_AD:
                        Toast.makeText(WebtoonList.this, "광고", Toast.LENGTH_SHORT).show();
                        break;
                    case WebtoonListAdapter.ITEM_COOKIE:
                        Toast.makeText(WebtoonList.this, "미리보기", Toast.LENGTH_SHORT).show();
                        break;
                    case WebtoonListAdapter.ITEM_WEBTOON_LIST:
                        Intent intent = new Intent(WebtoonList.this, WebtoonViewer.class);
                        l.setRead(true);

                        LinearLayout llPage = view.findViewById(R.id.list_page);//읽은척
                        TextView tvTitle = view.findViewById(R.id.list_title);
                        llPage.setBackgroundResource(R.drawable.read_mark);
                        tvTitle.setTextColor(getResources().getColor(R.color.blackfontexplain));

                        String str = l.getTvTitle();
                        intent.putExtra("webtoonTitle", str);//웹툰 회차 이름 전송
                        startActivity(intent);
                        break;
                }
            }
        });
        listView.setAdapter(adapter);
    }

}
