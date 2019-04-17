package com.example.myfirstapp.WebtoonViewer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.WebtoonComment.WebtoonCommentActivity;

public class WebtoonViewerActivity extends AppCompatActivity {

    private Context context;

    private TextView tvContentName;
    private Intent intentGet;
    private LinearLayout llContentLike;
    private LinearLayout llComment;

    private void init(){
        tvContentName = findViewById(R.id.content_name);
        llContentLike = findViewById(R.id.webtoon_viewer_linear_layout_like);
        llComment = findViewById(R.id.webtoon_viewer_linear_layout_comment);

        getIntentAndSet();
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWebtoonCommentActivity();
            }
        });
    }

    private void getIntentAndSet(){
        intentGet = getIntent();
        String str = intentGet.getExtras().getString("content_name");
        tvContentName.setText(str);
    }
    private void toWebtoonCommentActivity(){
        Intent intent = new Intent(context, WebtoonCommentActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_viewer);

        context=this;
        init();

    }
}
