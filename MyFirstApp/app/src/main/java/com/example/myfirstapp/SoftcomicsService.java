package com.example.myfirstapp;

import com.example.myfirstapp.Login.Entities.ResponseLoginData;
import com.example.myfirstapp.Main.Entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.Main.Entities.ResponseWebtoonListData;
import com.example.myfirstapp.MemberInformation.Entities.RequestWithdrawalData;
import com.example.myfirstapp.MemberInformation.Entities.ResponseWithdrawalData;
import com.example.myfirstapp.SignUp.Entities.ResponseSignUpData;
import com.example.myfirstapp.SignUp.Entities.RequestSignUpData;
import com.example.myfirstapp.WebtoonComment.Entities.RequestAddCommentData;
import com.example.myfirstapp.WebtoonComment.Entities.RequestCommentNoData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseAddCommentData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseCommentBehaviorData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponseAddLikeContentData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseCommentData;
import com.example.myfirstapp.common.Entities.RequestComicNoData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseAddAttentionWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseAddLikeWebtoonData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseGetFirstStoryData;
import com.example.myfirstapp.WebtoonContentsList.Entities.ResponseWebtoonContentsListData;
import com.example.myfirstapp.WebtoonViewer.Entities.RequestPutRatingData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponsePutRatingData;
import com.example.myfirstapp.WebtoonViewer.Entities.ResponseWebtoonContentViewData;
import com.example.myfirstapp.common.Entities.RequestContentNoData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SoftcomicsService {
    //API 1번 회원가입
    @POST("/user")
    Call<ResponseSignUpData> signUp(@Body RequestSignUpData memberData);

    //API 2번 회원탈퇴
    @HTTP(method = "DELETE", path = "/user", hasBody = true)
    Call<ResponseWithdrawalData> withdrawal(@Body RequestWithdrawalData requestWithdrawalData);

    //API 3번 로그인
    @GET("/token/{id}/{pw}")
    Call<ResponseLoginData> login(@Path("id") String id, @Path("pw") String pw);

    //API 4번 마이 관심웹툰리스트 보기
    @GET("/my/comic/list")
    Call<ResponseMyWebtoonListData> getMyWebtoonList(@Header("x-access-token") String token);

    //API 5번 웹툰 전체보기
    @GET("/comic/all")
    Call<ResponseWebtoonListData> getAllWebtoonList();

    //API 6번 요일별 웹툰 보기
    @GET("/comic/day/{day}")
    Call<ResponseWebtoonListData> getDaysWebtoonList(@Path("day") String day);

    //API 7번 웹툰 좋아요 누르기
    @POST("/comic/like")
    Call<ResponseAddLikeWebtoonData> addLikeWebtoon(@Body RequestComicNoData requestComicNoData);

    //API 9번 웹툰 컨텐츠 보기
    @GET("/comic/contentAll/{comicno}")
    Call<ResponseWebtoonContentsListData> getWebtoonContentsList(@Path("comicno") int comicNo);

    //API 10번 관심 웹툰 등록
    //@HTTP(method = "POST", path = "my/comic", hasBody = true)
    @POST("/my/comic")
    Call<ResponseAddAttentionWebtoonData> addAttentionWebtoon(@Body RequestComicNoData requestComicNoData);

    //API 11번 웹툰 첫화 얻어오기
    @GET("/comic/content/first/{comicno}")
    Call<ResponseGetFirstStoryData> getFirstStory(@Path("comicno") int comicNo);

    //API 12번 웹툰 컨텐츠 내용 얻어오기
    @GET("/comic/content/{contentno}")
    Call<ResponseWebtoonContentViewData> getContentImage(@Path("contentno") int contentNo);

    //API 13번 컨텐츠 좋아요 누르기
    @POST("/comic/content/like")
    Call<ResponseAddLikeContentData> addLikeContent(@Body RequestContentNoData requestContentNoData);

    //API 14번 컨텐츠 평점 주기
    @PUT("/comic/content/rate")
    Call<ResponsePutRatingData> putRating(@Body RequestPutRatingData requestPutRatingData);

    //API 15번 컨텐츠 댓글 쓰기
    @POST("comic/content/comment")
    Call<ResponseAddCommentData> addComment(@Body RequestAddCommentData requestAddCommentData);

    //API 16번 컨텐츠 댓글 전체보기
    @GET("/comic/content/comment/{contentno}")
    Call<ResponseCommentData> getAllComment(@Path("contentno") int contentno);

    //API 17번 컨텐츠 댓글 베스트 보기
    @GET("/comic/content/bestcomment/{contentno}")
    Call<ResponseCommentData> getBestComment(@Path("contentno") int contentno);

    //API 18번 컨텐츠 댓글 삭제
    @HTTP(method = "DELETE", path = "/comic/content/comment", hasBody = true)
    Call<ResponseCommentBehaviorData> deleteComment(@Body RequestCommentNoData requestCommentNoData);

    //API 19번 컨텐츠 댓글 좋아요 누르기
    @POST("/comic/content/comentLike")
    Call<ResponseCommentBehaviorData> addLikeComment(@Body RequestCommentNoData requestCommentNoData);

    //API 20번 컨텐츠 댓글 싫어요 누르기
    @POST("/comic/content/coment/dislike")
    Call<ResponseCommentBehaviorData> addDislikeComment(@Body RequestCommentNoData requestCommentNoData);
}