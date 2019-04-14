package com.example.myfirstapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.LoginActivity.entities.ResponseLoginData;
import com.example.myfirstapp.MainActivity.entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.SignUpActivity.entities.ResponseSignUpData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.MainActivity.entities.ResponseWebtoonListData;
import com.example.myfirstapp.MemberInformationActivity.entities.ResponseWithdrawalData;
import com.example.myfirstapp.SignUpActivity.entities.SoftComicsSignUpMemberData;
import com.example.myfirstapp.common.entities.WebtoonData;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class GlobalApplication extends Application {

    public static ArrayList<WebtoonData> webtoonList = new ArrayList<>();


    private static volatile GlobalApplication instance = null;

    public static GlobalApplication getGlobalApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }
    public static Retrofit softRetrofit;
    public static SoftcomicsService softcomicsService;
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private OkHttpClient headerClient = new OkHttpClient();

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

    public interface SoftcomicsService {
        //API 1번 회원가입
        @POST("user")
        Call<ResponseSignUpData> signUp(@Body SoftComicsSignUpMemberData memberData);

        //API 2번 회원탈퇴
        @HTTP(method = "DELETE", path = "user", hasBody = true)
        Call<ResponseWithdrawalData> withdrawal(@Body RequestBody body, @Header("x-access-token") String token);

        //API 3번 로그인
        @GET("token/{id}/{pw}")
        Call<ResponseLoginData> login(@Path("id") String id, @Path("pw") String pw);

        //API 4번 마이 웹툰리스트 보기
        @GET("my/comic/list")
        Call<ResponseMyWebtoonListData> getMyWebtoonList(@Header("x-access-token") String token);

        //API 5번 웹툰 전체보기
        @GET("comic/all")
        Call<ResponseWebtoonListData> getAllWebtoonList();

        //API 6번 요일별 웹툰 보기
        @GET("comic/day/{day}")
        Call<ResponseWebtoonListData> getDaysWebtoonList(@Path("day") String day);

        //API 9번 웹툰 컨텐츠 보기
        @GET("comic/contentAll/{comicno}")
        Call<ResponseWebtoonContentsListData> getWebtoonContentsList(@Path("comicno") int num);

        //API 10번 관심 웹툰 등록
        //@HTTP(method = "POST", path = "my/comic", hasBody = true)
        @POST("my/comic")
        Call<ResponseAddAttentionWebtoonData> addAttentionWebtoon(@Body RequestBody body, @Header("x-access-token") String token);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DataManager.initWebtoonDummyData(webtoonList);
        //softcomics.co.kr
        builder.addInterceptor(new AddHeaderInterceptor());
        headerClient = builder.build();
        softRetrofit = new Retrofit.Builder()
                .baseUrl("http://softcomics.co.kr/")
            //    .client(headerClient) 안됨...
                .client(client)//테스트용 클라이언트
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        softcomicsService = softRetrofit.create(SoftcomicsService.class);

        //requestPost();

    }
    public class AddHeaderInterceptor implements Interceptor{
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
            Toast.makeText(getGlobalApplicationContext(), "인터셉트!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .header("x-access-token", token)
                    .build();
            return chain.proceed(newRequest);
        }
    }
}