package com.example.myfirstapp.WebtoonContentsListActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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
import com.example.myfirstapp.WebtoonViewerActivity.WebtoonViewerActivity;
import com.example.myfirstapp.WebtoonContentsListActivity.adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.WebtoonContentsData;
import com.example.myfirstapp.common.entities.WebtoonData;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonContentsListActivity extends AppCompatActivity {
    private Intent intentGet;
    private TextView tvcomicName;
    private LinearLayout llAttention;
    private TextView tvHeart;
    private ImageView ivAttention;

    private WebtoonData comic;

    private ListView listView;
    private WebtoonContentsListAdapter adapter;

    private Context context;

    void init(){
        context = this;

    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_contents_list);

        init();

        //이전의 activity에서 얻어와 웹툰 제목 설정
        intentGet = getIntent();
        comic = (WebtoonData)intentGet.getExtras().getSerializable("comic");
        tvcomicName = findViewById(R.id.webtoonName);
        tvcomicName.setText(comic.getComicName());
        tvHeart = findViewById(R.id.number_of_heart);
        tvHeart.setText(Integer.toString(comic.getComicHeart()));

        listView = findViewById(R.id.listview_webtoon_contents);
        //서버로부터 웹툰 컨텐츠 데이터 가져오기
        adapter = new WebtoonContentsListAdapter(this);
        listView.setAdapter(adapter);//어댑터 연결
        Call<ResponseWebtoonContentsListData> responseWebtoonContentsListDataCall =
                GlobalApplication.softcomicsService.getWebtoonContentsList(comic.getComicNO());
        responseWebtoonContentsListDataCall.enqueue(new Callback<ResponseWebtoonContentsListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentsListData> call, Response<ResponseWebtoonContentsListData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()){
                        case 100://성공
                            List<WebtoonContentsData> list = response.body().getResult();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
                            for(int i=0; i<list.size(); i++){
                                boolean read = sharedPreferences.getBoolean(comic.getComicName()+i, false);
                                list.get(i).setRead(read);
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
                Toast.makeText(WebtoonContentsListActivity.this, "서버로부터 가져오지 못함", Toast.LENGTH_SHORT).show();
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
                SharedPreferences sharedPreferences = context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(comic.getComicName()+position, true);
                edit.commit();
                adapter.notifyDataSetChanged();

                String str = l.getContentName();
                intent.putExtra("webtoonTitle", str);//웹툰 회차 이름 전송
                startActivity(intent);

            }
        });
        ImageView ad = findViewById(R.id.ad);
        Glide.with(this).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(ad);

        //관심웹툰 추가
        llAttention=findViewById(R.id.attention_linear_layout);
        llAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context
                        .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                int comicNo=comic.getComicNO();
                RequestBody body =
                        RequestBody.create(MediaType.parse("text/plain"), Integer.toString(comicNo));
                Call<ResponseAddAttentionWebtoonData> attentionCall =
                        GlobalApplication.softcomicsService
                       .addAttentionWebtoon(
                               body, token);
                attentionCall.enqueue(new Callback<ResponseAddAttentionWebtoonData>() {
                    @Override
                    public void onResponse(Call<ResponseAddAttentionWebtoonData> call, Response<ResponseAddAttentionWebtoonData> response) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("관심웹툰");
                        alert.setPositiveButton("확인", null);
                        if(response.isSuccessful()){
                            switch (response.body().getCode()){
                                case 100:
                                    alert.setMessage(response.body().getMessage());
                                    alert.show();
                                    break;
                                    default:
                                        alert.setMessage("에러코드 "+response.body().getCode()+" : "+response.body().getMessage());
                                        alert.show();
                            }
                        }
                        else{
                            alert.setMessage("로그인이 필요합니다.");
                            alert.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseAddAttentionWebtoonData> call, Throwable t) {
                        Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                        System.out.println("로그 : "+t.toString());
                    }
                });
            }
        });

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