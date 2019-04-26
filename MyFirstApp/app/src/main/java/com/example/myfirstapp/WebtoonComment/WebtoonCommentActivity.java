package com.example.myfirstapp.WebtoonComment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.WebtoonComment.Adapter.CommentListAdapter;
import com.example.myfirstapp.WebtoonComment.Adapter.CommentTypePagerAdapter;
import com.example.myfirstapp.WebtoonComment.Entities.CommentData;
import com.example.myfirstapp.WebtoonComment.Entities.RequestAddCommentData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseAddCommentData;
import com.example.myfirstapp.common.Entities.WebtoonContentsData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonCommentActivity extends AppCompatActivity {

    private final int COMMENT_TYPE_BEST = 0;
    private final int COMMENT_TYPE_ALL = 1;

    private Context context;
    private SharedPreferences userDataPref;
    private Intent intentGet;
    private String user_id;

    private int commentNum;
    private TextView tvAllCommentsNum;

    private TabLayout tlComments;
    private ViewPager vpComments;

    private EditText etComment;
    private TextView tvCommentWriterId;
    private TextView tvAddComment;

    private ArrayList<ListView> commentListViewArrayList = new ArrayList<>();
    private ArrayList<CommentListAdapter> commentListAdapterArrayList = new ArrayList<>();
    private ArrayList<ArrayList<CommentData>> commentDataListArrayList = new ArrayList<>();

    private CommentTypePagerAdapter commentAdapter;
    private WebtoonContentsData content;

    private void init() {
        context = this;
        userDataPref =
                context.getSharedPreferences(getResources().getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        getIntentAndSet();

        tvAllCommentsNum = findViewById(R.id.webtoon_comment_all_comments_num);
        tlComments = findViewById(R.id.webtoon_comment_tab_layout);
        vpComments = findViewById(R.id.webtoon_comment_view_pager);
        etComment = findViewById(R.id.webtoon_comment_edittext);
        tvCommentWriterId = findViewById(R.id.webtoon_comment_writer_id);
        tvAddComment = findViewById(R.id.webtoon_comment_add_comment);

           commentAdapter = new CommentTypePagerAdapter(getSupportFragmentManager(), 2, context, content.getContentNo());
        /*for(int commentType = 0; commentType<2; commentType++){
            commentListViewArrayList.add(new ListView(context));
            commentDataListArrayList.add(new ArrayList<>());
            commentListAdapterArrayList
                    .add(new CommentListAdapter(commentDataListArrayList.get(commentType), context, commentType));
        }
        commentAdapter = new CommentTypePagerAdapter(commentListViewArrayList, context);
*/
        setTabLayout();
        setViewPager();
        bindTabLayoutAndViewPager();

        setCommentWriteLayout();

    }
    private void refresh(){
        commentAdapter.notifyDataSetChanged();
    }
    private void getIntentAndSet() {
        intentGet = getIntent();
        content = (WebtoonContentsData) intentGet.getExtras().getSerializable("content");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_comment);
        Singleton.isStartActivity = false;

        init();
        setCommentNum();
    }

    private void changeCommentEditText(boolean focus) {
        if (focus) {
            tvCommentWriterId.setVisibility(View.VISIBLE);
        } else {
            tvCommentWriterId.setVisibility(View.GONE);
        }
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

    private void setViewPager() {
        vpComments.setAdapter(commentAdapter);
    }

    private void setTabLayout() {
        tlComments.addTab(tlComments.newTab().setText("베스트댓글"));
        tlComments.addTab(tlComments.newTab().setText("전체댓글"));
    }


    private void setCommentWriteLayout() {
        user_id = userDataPref.getString("user_id", "로그인하세요.");
        tvCommentWriterId.setText(user_id);
        etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeCommentEditText(hasFocus);
            }
        });


        tvAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myComment = etComment.getText().toString();
                if (myComment.equals("")) {
                    Toast.makeText(context, "댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                etComment.setText("");
                etComment.clearFocus();
                requestAddComment(myComment);
            }
        });
    }


    private void setCommentNum() {
            tvAllCommentsNum.setText("전체 댓글");
    }
    private void requestAddComment(String myComment) {
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
                            refresh();
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

}
