package com.example.myfirstapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WebtoonViewer extends AppCompatActivity {

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

        m = MediaPlayer.create(WebtoonViewer.this, R.raw.bgm);
        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();//끝나면 메모리 절약을 위해 릴리즈해줘야함
            }
        });
        m.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m.start();

        Toast.makeText(this, "배경음악을 재생합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (m.isPlaying())
            m.pause();
        Toast.makeText(this, "배경음악을 멈춥니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        m.release();//오류남
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }
}
