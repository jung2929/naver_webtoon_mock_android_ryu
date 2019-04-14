package com.example.myfirstapp.WebtoonViewerActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class WebtoonViewerActivity extends AppCompatActivity {

    MediaPlayer m;
    TextView tvTitle;
    Intent intentGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_viewer);

        intentGet = getIntent();

        //웹툰 회차 이름 설정
        String str = intentGet.getExtras().getString("webtoonTitle");
        tvTitle = findViewById(R.id.webtoonTitle);
        tvTitle.setText(str);

    }
}
