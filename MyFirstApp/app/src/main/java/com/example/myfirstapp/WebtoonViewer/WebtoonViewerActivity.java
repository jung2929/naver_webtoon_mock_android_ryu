package com.example.myfirstapp.WebtoonViewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.WebtoonComment.WebtoonCommentActivity;
import com.example.myfirstapp.common.Entities.WebtoonContentsData;
import com.example.myfirstapp.WebtoonViewer.Adapter.ContentViewAdapter;
import com.example.myfirstapp.WebtoonViewer.Entities.RequestPutRatingData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponseAddLikeContentData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponsePutRatingData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponseWebtoonContentViewData;
import com.example.myfirstapp.WebtoonViewer.Entities.WebtoonContentViewData;
import com.example.myfirstapp.common.Entities.RequestContentNoData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonViewerActivity extends AppCompatActivity {

    private Context context;
    private Intent intentGet;

    private TextView tvContentName;
    private TextView tvPutRating;
    private ImageView ivContentLike;
    private LinearLayout llContentLike;
    private LinearLayout llComment;
    private RatingBar rbDisableRatingBar;
    private TextView tvRatingPoint;
    private TextView tvLikeNum;
    private TextView tvCommentNum;

    private ListView lvViewer;
    private ContentViewAdapter contentViewAdapter;
    private ArrayList<WebtoonContentViewData> webtoonImageDataList;

    private WebtoonContentsData content;


    private void init() {
        tvContentName = findViewById(R.id.content_name);
        tvPutRating = findViewById(R.id.webtoon_viewer_rating);
        ivContentLike = findViewById(R.id.webtoon_veiwer_content_like_image);
        llContentLike = findViewById(R.id.webtoon_viewer_linear_layout_like);
        llComment = findViewById(R.id.webtoon_viewer_linear_layout_comment);
        rbDisableRatingBar = findViewById(R.id.webtoon_viewer_rating_bar);
        tvRatingPoint = findViewById(R.id.webtoon_viewer_rating_point);
        lvViewer = findViewById(R.id.webtoon_viewer_list_view);
        tvLikeNum = findViewById(R.id.webtoon_viewer_like_num);
        tvCommentNum = findViewById(R.id.webtoon_viewer_comment_num);

        webtoonImageDataList = new ArrayList<>();
        contentViewAdapter = new ContentViewAdapter(context, webtoonImageDataList, getString(R.string.softcomics_url));

        getIntentAndSet();
        tvPutRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRatingDialog();
            }
        });
        llContentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAddLikeContent();
            }
        });
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWebtoonCommentActivity();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_viewer);
        Singleton.isStartActivity = false;

        context = this;
        init();
        requestGetContentViewData();

    }
    private void setContentLike(int like){
        if(content.getCheck() == 1) {
            ivContentLike.setImageResource(R.drawable.ic_like_checked);
        }
        else{
            ivContentLike.setImageResource(R.drawable.ic_like_unchecked);
        }
        tvLikeNum.setText(like+"");
    }
    private void createRatingDialog() {
        AlertDialog.Builder ratingDialog = new AlertDialog.Builder(context);
        View ratingView = createRatingView();
        ratingDialog.setView(ratingView);
        ratingDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView tvRate = ratingView.findViewById(R.id.rating_view_text);
                final int rate = Integer.parseInt(tvRate.getText().toString());
                requestPutRating(rate);
            }
        });
        ratingDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ratingDialog.show();
    }

    private View createRatingView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rating_view, null);
        RatingBar ratingBar = view.findViewById(R.id.rating_view_rating_bar);
        TextView textView = view.findViewById(R.id.rating_view_text);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //별의 개수로 따지므로 *2
                int rate = (int) (rating * 2);
                textView.setText(rate + "");
            }
        });
        return view;
    }

    private void requestPutRating(final int rate) {
        RequestPutRatingData putRatingData = new RequestPutRatingData(content.getContentNo(), rate);
        Call<ResponsePutRatingData> putRatingDataCall
                = Singleton.softcomicsService.putRating(putRatingData);
        putRatingDataCall.enqueue(new Callback<ResponsePutRatingData>() {
            @Override
            public void onResponse(Call<ResponsePutRatingData> call, Response<ResponsePutRatingData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "에러 : " + call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePutRatingData> call, Throwable t) {
                Toast.makeText(context, "서버연결실패 : " + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestAddLikeContent() {
        RequestContentNoData contentNoData = new RequestContentNoData(content.getContentNo());
        Call<ResponseAddLikeContentData> addLikeContentDataCall
                = Singleton.softcomicsService.addLikeContent(contentNoData);
        addLikeContentDataCall.enqueue(new Callback<ResponseAddLikeContentData>() {
            @Override
            public void onResponse(Call<ResponseAddLikeContentData> call, Response<ResponseAddLikeContentData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100: //성공
                            content.setCheck((content.getCheck()+1)%2);
                            setContentLike(response.body().getLike());
                            break;
                        default:
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, call.toString() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddLikeContentData> call, Throwable t) {
                Toast.makeText(context, "서버에러 : " + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentAndSet() {
        intentGet = getIntent();
        content = (WebtoonContentsData) intentGet.getExtras().getSerializable("content");
        if (content != null) {
            tvContentName.setText(content.getContentName());
        }

        float rating = 0;
        if (content != null) {
            rating = Float.parseFloat(content.getContentRating());
        }
        //별의 개수로 들어가므로 /2
        rbDisableRatingBar.setRating(rating / 2);
        tvRatingPoint.setText(rating + "");

    }

    private void toWebtoonCommentActivity() {
        if(Singleton.isStartActivity){
            return;
        }
        Singleton.isStartActivity = true;
        Intent intent = new Intent(context, WebtoonCommentActivity.class);
        intent.putExtra("content", content);
        startActivity(intent);
    }

    private void requestGetContentViewData() {
        Call<ResponseWebtoonContentViewData> getImage = Singleton.softcomicsService.getContentImage(5);
        getImage.enqueue(new Callback<ResponseWebtoonContentViewData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonContentViewData> call, Response<ResponseWebtoonContentViewData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            try {
                                List<WebtoonContentViewData> imageList = response.body().getResult();
                                webtoonImageDataList = new ArrayList<>(imageList);
                                contentViewAdapter.setData(webtoonImageDataList);

                                content.setCheck(response.body().getCheck());
                                setContentLike(content.getContentHeart());

                                tvCommentNum.setText(response.body().getComment()+"");
                            } catch (Exception e) {
                                Toast.makeText(context, "불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            Toast.makeText(context, "에러?", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseWebtoonContentViewData> call, Throwable t) {
                Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
