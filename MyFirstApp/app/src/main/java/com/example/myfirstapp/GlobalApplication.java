package com.example.myfirstapp;

import android.app.Application;
import android.content.Context;

import com.example.myfirstapp.entities.ResponseLoginData;
import com.example.myfirstapp.entities.ResponseSignUpData;
import com.example.myfirstapp.entities.ResponseWithdrawalData;
import com.example.myfirstapp.entities.SoftComicsMemberData;
import com.example.myfirstapp.entities.WebtoonListData;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class GlobalApplication extends Application {




    public static ArrayList<WebtoonListData> webtoonList = new ArrayList<>();


    private static volatile GlobalApplication instance = null;

    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }
    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }


        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return GlobalApplication.getGlobalApplicationContext();
                }
            };
        }
    }


    public static Retrofit softRetrofit;
    public static softcomicsService softcomicsservice;
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();
    public void requestPost(){

        //Request Body에 서버에 보낼 데이터 작성
        RequestBody requestBody = new FormBody.Builder().add("id", "id").add("pw", "pw").add("subpw", "pw").add("email", "pw@gmail.com").add("tel", "01111123123").build();

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        Request request = new Request.Builder().url("http://softcomics.co.kr/user").post(requestBody).build();

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println(e.toString()+" ok 에러");
                }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                System.out.println("성공 :"+response.body().string());
            }
        });
    }
    public interface  softcomicsService {
        @POST("user")
        Call<ResponseSignUpData> signUp(@Body SoftComicsMemberData memberData);
        @GET("token/{id}/{pw}")
        Call<ResponseLoginData> login(@Path("id") String id, @Path("pw") String pw);
        @HTTP(method = "DELETE", path = "user", hasBody = true)
        Call<ResponseWithdrawalData> withdrawal(@Body String pw, @Header ("x-access-token") String token);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DataManager.initWebtoonDummyData(webtoonList);

        //softcomics.co.kr
        softRetrofit = new Retrofit.Builder()
                .baseUrl("http://softcomics.co.kr/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        softcomicsservice=softRetrofit.create(softcomicsService.class);
        // requestPost();



        KakaoSDK.init(new KakaoSDKAdapter());
    }
}