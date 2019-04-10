package com.example.myfirstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.entities.WebtoonContentsData;

public class WebtoonListActivity extends AppCompatActivity {
    Intent intentGet;
    TextView tvWebtoonName;
    String webtoonName;

    ListView listView;
    WebtoonContentsListAdapter adapter;
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

        adapter = new WebtoonContentsListAdapter(this);
        adapter.addItem(WebtoonContentsListAdapter.ITEM_AD);
        adapter.addItem(WebtoonContentsListAdapter.ITEM_COOKIE);

        for(int i=1; i<20; i++){
            adapter.addItem(new WebtoonContentsData(
                    null, webtoonName+" "+i+"화", "9.99", "19.03."+i, false));
        }
        listView = findViewById(R.id.listView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonContentsData l = (WebtoonContentsData)listView.getItemAtPosition(position);
                switch (l.getItemType()){
                    case WebtoonContentsListAdapter.ITEM_AD:
                        Toast.makeText(WebtoonListActivity.this, "광고", Toast.LENGTH_SHORT).show();
                        break;
                    case WebtoonContentsListAdapter.ITEM_COOKIE:
                        Toast.makeText(WebtoonListActivity.this, "미리보기", Toast.LENGTH_SHORT).show();
                        break;
                    case WebtoonContentsListAdapter.ITEM_WEBTOON_LIST:
                        Intent intent = new Intent(WebtoonListActivity.this, WebtoonViewerActivity.class);
                        l.setRead(true);

                        LinearLayout llPage = view.findViewById(R.id.list_page);//읽은척
                        TextView tvTitle = view.findViewById(R.id.webtoon_title);
                        llPage.setBackgroundResource(R.drawable.read_mark);
                        tvTitle.setTextColor(getResources().getColor(R.color.blackfontexplain));

                        String str = l.getTitle();
                        intent.putExtra("webtoonTitle", str);//웹툰 회차 이름 전송
                        startActivity(intent);
                        break;
                }
            }
        });
        listView.setAdapter(adapter);

        FrameLayout back = findViewById(R.id.btn_back_webtoon_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
