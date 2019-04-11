package com.example.myfirstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.entities.WebtoonContentsData;

public class WebtoonContentsListActivity extends AppCompatActivity {
    private Intent intentGet;
    private TextView tvWebtoonName;
    private String webtoonName;

    private ListView listView;
    private WebtoonContentsListAdapter adapter;
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_contents_list);

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

        for(int i=1; i<20; i++){
            adapter.addItem(new WebtoonContentsData(
                    null, webtoonName+" "+i+"화", "9.99", "19.03."+i, false));
        }
        listView = findViewById(R.id.listview_webtoon_contents);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonContentsData l = (WebtoonContentsData) listView.getItemAtPosition(position);
                Intent intent = new Intent(WebtoonContentsListActivity.this, WebtoonViewerActivity.class);
                l.setRead(true);

                LinearLayout llPage = view.findViewById(R.id.list_page);//읽은척
                TextView tvTitle = view.findViewById(R.id.webtoon_title);
                llPage.setBackgroundResource(R.drawable.read_mark);
                tvTitle.setTextColor(getResources().getColor(R.color.blackfontexplain));

                String str = l.getTitle();
                intent.putExtra("webtoonTitle", str);//웹툰 회차 이름 전송
                startActivity(intent);

            }
        });
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        ImageView ad = findViewById(R.id.ad);
        Glide.with(this).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(ad);

        FrameLayout back = findViewById(R.id.btn_back_webtoon_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
