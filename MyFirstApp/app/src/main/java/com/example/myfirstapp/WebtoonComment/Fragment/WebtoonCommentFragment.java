package com.example.myfirstapp.WebtoonComment.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.WebtoonComment.Adapter.CommentListAdapter;
import com.example.myfirstapp.WebtoonComment.Entities.CommentData;
import com.example.myfirstapp.WebtoonComment.Entities.RequestCommentNoData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseCommentBehaviorData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseCommentData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonCommentFragment extends Fragment {

    private int COMMENT_TYPE;
    private final int COMMENT_TYPE_BEST = 0;
    private final int COMMENT_TYPE_ALL = 1;
    private int contentNo;

    private Context context;
    private SharedPreferences userDataPref;
    private String userId;

    private CommentListAdapter commentListAdapter;
    private ArrayList<CommentData> commentList;
    private ListView lvListView;

    private void init(final View v){
        context = getContext();
        getArgumentsAndSet();
        userDataPref = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        userId=userDataPref.getString("user_id", "");

        lvListView = v.findViewById(R.id.webtoon_best_comment_list_view);

        commentList = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(commentList, context, COMMENT_TYPE);
        lvListView.setAdapter(commentListAdapter);
    }
    private void getArgumentsAndSet(){
        contentNo = getArguments().getInt("content_no");
        COMMENT_TYPE = getArguments().getInt("comment_type");
    }
    private void requestGetCommentList(){
        Call<ResponseCommentData> commentDataCall;
        if(COMMENT_TYPE == COMMENT_TYPE_BEST) {
                    commentDataCall = Singleton.softcomicsService.getBestComment(contentNo);
        }else{
            commentDataCall= Singleton.softcomicsService.getAllComment(contentNo);
        }
        commentDataCall.enqueue(new Callback<ResponseCommentData>() {
            @Override
            public void onResponse(Call<ResponseCommentData> call, Response<ResponseCommentData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()){
                        case 100 : //성공
                            List<CommentData> list = response.body().getData();
                            commentList = new ArrayList<>(list);
                            for(CommentData e : commentList){
                                if(e.getUserId().equals(userId)){
                                    e.setWrittenMe(true);
                                }
                            }
                            commentListAdapter.setData(commentList);
                            break;
                            default:
                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, ""+call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommentData> call, Throwable t) {
                Toast.makeText(context, "에러!!!"+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webtoon_comment, container, false);
        init(v);
        Log.d("프래그먼트", COMMENT_TYPE+"");
        requestGetCommentList();
        return v;
    }


}
