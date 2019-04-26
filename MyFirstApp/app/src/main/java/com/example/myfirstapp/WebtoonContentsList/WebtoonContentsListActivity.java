package com.example.myfirstapp.WebtoonContentsList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.common.Entities.RequestComicNoData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseAddLikeWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseGetFirstStoryData;
import com.example.myfirstapp.WebtoonViewer.WebtoonViewerActivity;
import com.example.myfirstapp.WebtoonContentsList.Adapter.WebtoonContentsListAdapter;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.common.Entities.WebtoonContentsData;
import com.example.myfirstapp.common.Entities.WebtoonData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonContentsListActivity extends AppCompatActivity {
    private Intent intentGet;
    private SharedPreferences webtoonPref;

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
    private ArrayList<WebtoonContentsData> contentsList = new ArrayList<>();

    private Context context;

    private SharedPreferences WebtoonDataSharedPreference;
    private SharedPreferences.Editor WebtoonDataSharedPreferenceEdit;
    private SharedPreferences userDataSharedPreferences;
    private String token;
    private RequestComicNoData requestComicNoData;

    private int mLastContentNo;  //페이징 처리 request로 보낼 변수
    private boolean isLastItem = false; //리스트뷰의 끝인지
    private boolean isSendPagingRequest = false;   //페이징처리 요청을 보냈는지
    private ProgressBar progressBar;
    private final int PAGING_OFFSET = 3;    //페이징으로 한번에 불러오는 데이터 수

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_contents_list);
        init();
        requestGetWebtoonContentsList();
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
        progressBar = findViewById(R.id.webtoon_contentslist_progressbar);
        ivLike = findViewById(R.id.webtoon_contentslist_like_image);

        webtoonContentsAdapter = new WebtoonContentsListAdapter(this, contentsList);


        intentGet = getIntent();
        comic = (WebtoonData) intentGet.getExtras().getSerializable("comic");
        Glide.with(this).load("https://ssl.pstatic.net/tveta/libs/1228/1228325/8900f29613ccef494352_20190405142447995.jpg").into(ad);
        requestComicNoData = new RequestComicNoData(comic.getComicNO());


        tvcomicName.setText(comic.getComicName());
        tvLike.setText(comic.getComicHeart() + "");

        WebtoonDataSharedPreference =
                context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
        WebtoonDataSharedPreferenceEdit = WebtoonDataSharedPreference.edit();
        userDataSharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        token = userDataSharedPreferences.getString("token", "");
    }

    private void setReadTagAndSyncWithAdapter(List<WebtoonContentsData> list) {
        webtoonPref = context.getSharedPreferences("WebtoonTemporaryData", Context.MODE_PRIVATE);
        for (int i = 0; i < list.size(); i++) {
            boolean read = webtoonPref.getBoolean(list.get(i).getContentNo() + "", false);
            list.get(i).setRead(read);
        }
        contentsList.addAll(list);
        if (contentsList.size() == 0) {
            isSendPagingRequest = true;
            return;
        }
        mLastContentNo = contentsList.get(contentsList.size() - 1).getContentNo();
        webtoonContentsAdapter.setDataList(contentsList);
        //  setListViewHeightBasedOnChildren(listView);
    }

    private void setComicLikeImage() {
        if (comic.getCheck() == 1) {
            ivLike.setImageResource(R.drawable.ic_like_checked);
        } else {
            ivLike.setImageResource(R.drawable.ic_like_unchecked);
        }
    }

    private void requestGetWebtoonContentsList() {
        Call<ResponseWebtoonContentsListData> responseWebtoonContentsListDataCall =
                Singleton.softcomicsService.getWebtoonContentsList(comic.getComicNO());
        responseWebtoonContentsListDataCall.enqueue(new Callback<ResponseWebtoonContentsListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentsListData> call, Response<ResponseWebtoonContentsListData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            List<WebtoonContentsData> list = response.body().getResult();
                            setReadTagAndSyncWithAdapter(list);
                            comic.setCheck(response.body().getCheck());
                            setComicLikeImage();
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
            }
        });
    }

    private void requestGetPagingContentList() {
        Call<ResponseWebtoonContentsListData> getPaging =
                Singleton.softcomicsService.getPagingContentList(mLastContentNo);
        getPaging.enqueue(new Callback<ResponseWebtoonContentsListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentsListData> call, Response<ResponseWebtoonContentsListData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            List<WebtoonContentsData> list = response.body().getResult();
                            if (list.size() == PAGING_OFFSET) {
                                isSendPagingRequest = false;
                            }
                            if (list != null) {
                                list.remove(0);
                            }
                            setReadTagAndSyncWithAdapter(list);
                            progressBar.setVisibility(View.GONE);
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
            }
        });
    }

    private void setContentsListView() {
        listView.setAdapter(webtoonContentsAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonContentsData contentData = (WebtoonContentsData) listView.getItemAtPosition(position);
                toWebtoonViewerActivity(contentData);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isLastItem && !isSendPagingRequest) {
                    progressBar.setVisibility(View.VISIBLE);
                    isSendPagingRequest = true;
                    requestGetPagingContentList();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isLastItem = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
                if (isLastItem && !isSendPagingRequest) {
                    progressBar.setVisibility(View.VISIBLE);
                    isSendPagingRequest = true;
                    requestGetPagingContentList();
                }
                Log.d("스크롤 마지막임? : ", firstVisibleItem + ", " + visibleItemCount + ", " + totalItemCount + ", " + isLastItem);
            }
        });
    }

    private void toWebtoonViewerActivity(WebtoonContentsData contentData) {
        if (Singleton.isStartActivity) {
            return;
        }
        Singleton.isStartActivity = true;

        Intent intent = new Intent(WebtoonContentsListActivity.this, WebtoonViewerActivity.class);
        contentData.setRead(true);
        WebtoonDataSharedPreferenceEdit.putBoolean(contentData.getContentNo() + "", true);//ContentNo은 primary key임
        WebtoonDataSharedPreferenceEdit.commit();
        webtoonContentsAdapter.notifyDataSetChanged();

        intent.putExtra("content", contentData);
        startActivity(intent);
    }

    private void requestAddAttentionWebtoon() {
        llAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseAddAttentionWebtoonData> attentionCall =
                        Singleton.softcomicsService
                                .addAttentionWebtoon(requestComicNoData);
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
                                    comic.setCheck((comic.getCheck()+1)%2);
                                    setComicLikeImage();
                                    break;
                                default:
                                    alert.setMessage(response.body().getCode() + " : " + response.body().getMessage());
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
                                        toWebtoonViewerActivity(first);
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
