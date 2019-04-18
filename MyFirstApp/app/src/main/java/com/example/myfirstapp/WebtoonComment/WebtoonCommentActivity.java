package com.example.myfirstapp.WebtoonComment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.WebtoonComment.Adapter.CommentTypePagerAdapter;
import com.example.myfirstapp.WebtoonComment.Entities.RequestAddCommentData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseAddCommentData;
import com.example.myfirstapp.WebtoonContentsList.Entities.WebtoonContentsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonCommentActivity extends AppCompatActivity {

    Context context;
    SharedPreferences userDataShardPref;
    Intent intentGet;

    TabLayout tlComments;
    ViewPager vpComments;
    EditText etComment;
    TextView tvCommentWriterId;
    TextView tvAddComment;

    CommentTypePagerAdapter commentAdapter;
    WebtoonContentsData content;

    private void init() {
        context = this;
        userDataShardPref =
                context.getSharedPreferences(getResources().getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);

        tlComments = findViewById(R.id.webtoon_comment_tab_layout);
        vpComments = findViewById(R.id.webtoon_comment_view_pager);
        etComment = findViewById(R.id.webtoon_comment_edittext);
        tvCommentWriterId = findViewById(R.id.webtoon_comment_writer_id);
        tvAddComment = findViewById(R.id.webtoon_comment_add_comment);

        commentAdapter=new CommentTypePagerAdapter(getSupportFragmentManager(),2, context, content.getContentNo());

        getIntentAndSet();
        setTabLayout();
        setViewPager();
        bindTabLayoutAndViewPager();

        setCommentWriteLayout();

    }

    private void getIntentAndSet() {
        intentGet = getIntent();
        content = (WebtoonContentsData) intentGet.getExtras().getSerializable("content");
    }

    private void setCommentWriteLayout() {
        String id = userDataShardPref.getString("user_id", "로그인하세요.");
        tvCommentWriterId.setText(id);
        etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeCommentEditText(hasFocus);
            }
        });


        tvAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAddComment();
            }
        });
    }

    private void requestAddComment() {
        String myComment = etComment.getText().toString();
        RequestAddCommentData requestAddCommentData =
                new RequestAddCommentData(content.getContentNo(), myComment);
        Call<ResponseAddCommentData> addCommentDataCall =
                Singleton.softcomicsService.addComment(requestAddCommentData);
        addCommentDataCall.enqueue(new Callback<ResponseAddCommentData>() {
            @Override
            public void onResponse(Call<ResponseAddCommentData> call, Response<ResponseAddCommentData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100: //성공
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, call.toString() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddCommentData> call, Throwable t) {
                Toast.makeText(context, "에러 : " + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeCommentEditText(final boolean focus) {
        if (focus == true) {
            tvCommentWriterId.setVisibility(View.VISIBLE);
        } else {
            tvCommentWriterId.setVisibility(View.GONE);
        }
    }

    private void setTabLayout() {
        tlComments.addTab(tlComments.newTab().setText("베스트댓글"));
        tlComments.addTab(tlComments.newTab().setText("전체댓글"));
    }

    private void setViewPager() {

    }

    private void bindTabLayoutAndViewPager() {
        tlComments.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                vpComments.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vpComments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tlComments.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_comment);

        init();
    }
}
