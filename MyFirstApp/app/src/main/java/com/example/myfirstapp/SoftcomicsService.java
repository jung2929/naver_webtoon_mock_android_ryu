package com.example.myfirstapp;

import com.example.myfirstapp.LoginActivity.entities.ResponseLoginData;
import com.example.myfirstapp.MainActivity.entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.MainActivity.entities.ResponseWebtoonListData;
import com.example.myfirstapp.MemberInformationActivity.entities.RequestWithdrawalData;
import com.example.myfirstapp.MemberInformationActivity.entities.ResponseWithdrawalData;
import com.example.myfirstapp.SignUpActivity.entities.ResponseSignUpData;
import com.example.myfirstapp.SignUpActivity.entities.RequestSignUpData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.RequestComicNoData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseAddLikeWebtoonData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseGetFirstStoryData;
import com.example.myfirstapp.WebtoonContentsListActivity.entities.ResponseWebtoonContentsListData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SoftcomicsService {
    //API 1번 회원가입
    @POST("user")
    Call<ResponseSignUpData> signUp(@Body RequestSignUpData memberData);

    //API 2번 회원탈퇴
    @HTTP(method = "DELETE", path = "user", hasBody = true)
    Call<ResponseWithdrawalData> withdrawal(@Body RequestWithdrawalData requestWithdrawalData);

    //API 3번 로그인
    @GET("token/{id}/{pw}")
    Call<ResponseLoginData> login(@Path("id") String id, @Path("pw") String pw);

    //API 4번 마이 관심웹툰리스트 보기
    @GET("my/comic/list")
    Call<ResponseMyWebtoonListData> getMyWebtoonList(@Header("x-access-token") String token);

    //API 5번 웹툰 전체보기
    @GET("comic/all")
    Call<ResponseWebtoonListData> getAllWebtoonList();

    //API 6번 요일별 웹툰 보기
    @GET("comic/day/{day}")
    Call<ResponseWebtoonListData> getDaysWebtoonList(@Path("day") String day);

    //API 7번 웹툰 좋아요 누르기
    @POST("comic/like")
    Call<ResponseAddLikeWebtoonData> addLikeWebtoon(@Body RequestComicNoData requestComicNoData);

    //API 9번 웹툰 컨텐츠 보기
    @GET("comic/contentAll/{comicno}")
    Call<ResponseWebtoonContentsListData> getWebtoonContentsList(@Path("comicno") int comicNo);

    //API 10번 관심 웹툰 등록, 아직 RequestBody 미구현
    //@HTTP(method = "POST", path = "my/comic", hasBody = true)
    @POST("my/comic")
    Call<ResponseAddAttentionWebtoonData> addAttentionWebtoon(@Body RequestComicNoData requestComicNoData, @Header("x-access-token") String token);

    //API 11번
    @GET("comic/content/first/{comicno}")
    Call<ResponseGetFirstStoryData> getFirstStory(@Path("comicno") int comicNo);
}