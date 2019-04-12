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
import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.entities.WebtoonContentsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonContentsListActivity extends AppCompatActivity {
    private Intent intentGet;
    private TextView tvcomicName;
    private String comicName;
    private int comicNo;

    private ListView listView;
    private WebtoonContentsListAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_contents_list);

        //이전의 activity에서 얻어와 웹툰 제목 설정
        intentGet = getIntent();
        comicName = intentGet.getExtras().getString("comic_name");
        comicNo = intentGet.getExtras().getInt("comic_no");
        tvcomicName = findViewById(R.id.webtoonName);
        tvcomicName.setText(comicName);

        listView = findViewById(R.id.listview_webtoon_contents);
        //서버로부터 가져오기
        adapter = new WebtoonContentsListAdapter(this);
        listView.setAdapter(adapter);//어댑터 연결
        Call<ResponseWebtoonContentsListData> responseWebtoonContentsListDataCall =
                GlobalApplication.softcomicsservice.getWebtoonContentsList(comicNo);
        responseWebtoonContentsListDataCall.enqueue(new Callback<ResponseWebtoonContentsListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentsListData> call, Response<ResponseWebtoonContentsListData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()){
                        case 100://성공
                            List<WebtoonContentsData> list = response.body().getResult();
                            System.out.println("성공 !! comicNo:" +comicNo+", "+ list.size() +"개의 목록");
                            for(int i=0; i<list.size(); i++){
                                adapter.addItem(list.get(i));
                            }
                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(listView);
                            break;
                            default:
                                Toast.makeText(WebtoonContentsListActivity.this, "에러코드 : " +response.body().getCode(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(WebtoonContentsListActivity.this, "못불러옴", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseWebtoonContentsListData> call, Throwable t) {
                Toast.makeText(WebtoonContentsListActivity.this, "서버로부터 가져오지 못함 : ", Toast.LENGTH_SHORT).show();
                System.out.println("실패원인 " +t.toString());
            }
        });

        //리스트뷰 세팅
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

                String str = l.getContentName();
                intent.putExtra("webtoonTitle", str);//웹툰 회차 이름 전송
                startActivity(intent);

            }
        });
        ImageView ad = findViewById(R.id.ad);
        Glide.with(this).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(ad);

        //뒤로가기
        FrameLayout back = findViewById(R.id.btn_back_webtoon_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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

}
