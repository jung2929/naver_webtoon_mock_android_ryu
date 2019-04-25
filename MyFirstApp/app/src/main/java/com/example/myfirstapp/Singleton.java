package com.example.myfirstapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myfirstapp.common.Entities.WebtoonData;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton extends Application {

    public static ArrayList<WebtoonData> webtoonList = new ArrayList<>();

    public static boolean isStartActivity = false;


    private static volatile Singleton instance = null;

    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getSingletonInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static
    MyFirebaseMessagingService firebaseMessagingService;

    public static Retrofit softRetrofit;
    public static SoftcomicsService softcomicsService;
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private OkHttpClient headerClient = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getSingletonInstance();

        DataManager.initWebtoonDummyData(webtoonList);
        //softcomics.co.kr
        builder.addInterceptor(new AddHeaderInterceptor());
        headerClient = builder.build();
        softRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.softcomics_url))
                .client(headerClient)//
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        softcomicsService = softRetrofit.create(SoftcomicsService.class);

        //requestPost();

        //fcm
        firebaseMessagingService = new MyFirebaseMessagingService();
    }

    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            Request.Builder newRequest = request.newBuilder();
            if (!token.equals("")) {
                newRequest.header("x-access-token", token);
            }
            return chain.proceed(newRequest.build());
        }
    }

    public void requestGet(int comicno) {
        Request request = new Request.Builder().url("http://softcomics.co.kr/comic/contentAll/" + comicno).get().build();
        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println(e.toString() + " ok 에러");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                System.out.println("성공 :" + response.body().string());
            }
        });
    }

    public void requestPost() {

        //Request Body에 서버에 보낼 데이터 작성
        RequestBody requestBody = new FormBody.Builder().add("comicno", "1").build();

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        Request request = new Request.Builder().url("http://softcomics.co.kr/my/comic").post(requestBody).build();

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println(e.toString() + " ok 에러");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                System.out.println("okhttp성공 :" + response.body().string());
            }
        });
    }


}