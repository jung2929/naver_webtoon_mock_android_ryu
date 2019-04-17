package com.example.myfirstapp.WebtoonContentsList;

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
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.WebtoonContentsList.entities.RequestComicNoData;
import com.example.myfirstapp.WebtoonContentsList.entities.ResponseAddLikeWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.entities.ResponseGetFirstStoryData;
import com.example.myfirstapp.WebtoonViewer.WebtoonViewerActivity;
import com.example.myfirstapp.WebtoonContentsList.adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.WebtoonContentsList.entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.WebtoonContentsList.entities.WebtoonContentsData;
import com.example.myfirstapp.common.Entities.WebtoonData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonContentsListActivity extends AppCompatActivity {
    private Intent intentGet;
    private TextView tvcomicName;
    private TextView tvLike;
    private LinearLayout llAttention;
    private LinearLayout llLike;
    private ImageView ivAttention;
    private ImageView ivLike;
    private TextView tvToFirstStory;
    private FrameLayout back;
    private ImageView ad;

    private WebtoonData comic;

    private ListView listView;
    private WebtoonContentsListAdapter webtoonContentsAdapter;
    private ArrayList<WebtoonContentsData> contentsList;

    private Context context;

    private SharedPreferences WebtoonDataSharedPreference;
    private SharedPreferences.Editor WebtoonDataSharedPreferenceEdit;
    private SharedPreferences userDataSharedPreferences;
    private String token;
    RequestComicNoData requestComicNoData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_contents_list);

        init();
        getWebtoonContentsList();
        setContentsListView();
        requestAddAttentionWebtoon();
        requestAddLikeWebtoon();
        requestGetFirstStory();
        setBackButton();
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

    private void init() {
        context = this;

        tvcomicName = findViewById(R.id.webtoonName);
        listView = findViewById(R.id.listview_webtoon_contents);
        ad = findViewById(R.id.ad);
        llAttention = findViewById(R.id.add_attention_linear_layout);
        llLike = findViewById(R.id.add_like_linear_layout);
        tvLike = findViewById(R.id.number_of_like);
        tvToFirstStory = findViewById(R.id.text_view_first_story);
        back = findViewById(R.id.btn_back_webtoon_list);

        webtoonContentsAdapter = new WebtoonContentsListAdapter(this, contentsList);


        intentGet = getIntent();
        comic = (WebtoonData) intentGet.getExtras().getSerializable("comic");
        Glide.with(this).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(ad);
        requestComicNoData = new RequestComicNoData(comic.getComicNO());


        tvcomicName.setText(comic.getComicName());
        tvLike.setText(Integer.toString(comic.getComicHeart()));

        WebtoonDataSharedPreference =
                context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
        WebtoonDataSharedPreferenceEdit = WebtoonDataSharedPreference.edit();
        userDataSharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        token = userDataSharedPreferences.getString("token", "");
    }

    private void getWebtoonContentsList() {
        Call<ResponseWebtoonContentsListData> responseWebtoonContentsListDataCall =
                Singleton.softcomicsService.getWebtoonContentsList(comic.getComicNO());
        responseWebtoonContentsListDataCall.enqueue(new Callback<ResponseWebtoonContentsListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentsListData> call, Response<ResponseWebtoonContentsListData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            List<WebtoonContentsData> list = response.body().getResult();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
                            for (int i = 0; i < list.size(); i++) {
                                boolean read = sharedPreferences.getBoolean(comic.getComicName() + i, false);
                                list.get(i).setRead(read);
                            }
                            contentsList = new ArrayList<>(list);
                            webtoonContentsAdapter.setDataList(contentsList);
                            setListViewHeightBasedOnChildren(listView);
                            break;
                        default:
                            Toast.makeText(WebtoonContentsListActivity.this, "에러코드 : " + response.body().getCode(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WebtoonContentsListActivity.this, "못불러옴", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseWebtoonContentsListData> call, Throwable t) {
                Toast.makeText(WebtoonContentsListActivity.this, "서버로부터 가져오지 못함", Toast.LENGTH_SHORT).show();
                System.out.println("실패원인 " + t.toString());
            }
        });
    }

    private void setContentsListView() {
        listView.setAdapter(webtoonContentsAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toWebtoonViewerActivity(position);
            }
        });
    }
    private void toWebtoonViewerActivity(int position){
        WebtoonContentsData l = (WebtoonContentsData) listView.getItemAtPosition(position);
        Intent intent = new Intent(WebtoonContentsListActivity.this, WebtoonViewerActivity.class);
        l.setRead(true);
        WebtoonDataSharedPreferenceEdit.putBoolean(comic.getComicName() + position, true);
        WebtoonDataSharedPreferenceEdit.commit();
        webtoonContentsAdapter.notifyDataSetChanged();

        String str = l.getContentName();
        intent.putExtra("content_name", str);//웹툰 회차 이름 전송
        startActivity(intent);
    }
    private void requestAddAttentionWebtoon() {
        llAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseAddAttentionWebtoonData> attentionCall =
                        Singleton.softcomicsService
                                .addAttentionWebtoon(requestComicNoData, token);
                attentionCall.enqueue(new Callback<ResponseAddAttentionWebtoonData>() {
                    @Override
                    public void onResponse(Call<ResponseAddAttentionWebtoonData> call, Response<ResponseAddAttentionWebtoonData> response) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("관심웹툰");
                        alert.setPositiveButton("확인", null);
                        if (response.isSuccessful()) {
                            switch (response.body().getCode()) {
                                case 100:
                                    alert.setMessage(response.body().getMessage());
                                    alert.show();
                                    break;
                                default:
                                    alert.setMessage("에러코드 " + response.body().getCode() + " : " + response.body().getMessage());
                                    alert.show();
                            }
                        } else {
                            alert.setMessage("로그인이 필요합니다.");
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAddAttentionWebtoonData> call, Throwable t) {
                        Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void requestAddLikeWebtoon() {
        llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseAddLikeWebtoonData> likeCall =
                        Singleton.softcomicsService
                                .addLikeWebtoon(requestComicNoData);
                likeCall.enqueue(new Callback<ResponseAddLikeWebtoonData>() {
                    @Override
                    public void onResponse(Call<ResponseAddLikeWebtoonData> call, Response<ResponseAddLikeWebtoonData> response) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("좋아요");
                        alert.setPositiveButton("확인", null);
                        if (response.isSuccessful()) {
                            switch (response.body().getCode()) {
                                case 100://성공
                                    alert.setMessage(response.body().getMessage());
                                    alert.show();
                                    break;
                                default:
                                    alert.setMessage("에러코드 " + response.body().getCode() + " : " + response.body().getMessage());
                                    alert.show();
                            }
                        } else {
                            alert.setMessage("로그인이 필요합니다.");
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAddLikeWebtoonData> call, Throwable t) {
                        Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void changeAttentionImageView() {
        ivAttention.setImageResource(R.drawable.ic_attention_button_checked);
    }

    private void requestGetFirstStory() {
        tvToFirstStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseGetFirstStoryData> getFirstStoryCall =
                        Singleton.softcomicsService.getFirstStory(comic.getComicNO());
                getFirstStoryCall.enqueue(new Callback<ResponseGetFirstStoryData>() {
                    @Override
                    public void onResponse(Call<ResponseGetFirstStoryData> call, Response<ResponseGetFirstStoryData> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getCode()) {
                                case 100:
                                    WebtoonContentsData first = response.body().getWebtoonContentsData();
                                    if (first == null) {
                                        Toast.makeText(context, "웹툰이 존재하지 않음", Toast.LENGTH_SHORT).show();
                                    } else {
                                        toWebtoonViewerActivity(0);
                                    }
                                    break;
                                default:
                                    Toast.makeText(context, response.body().getCode() + "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "SQL 문법 에러", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGetFirstStoryData> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(context, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                            // logging probably not necessary
                        } else {
                            Toast.makeText(context, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                            // todo log to some central bug tracking service
                        }
                    }
                });
            }
        });
    }

    private void setBackButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
