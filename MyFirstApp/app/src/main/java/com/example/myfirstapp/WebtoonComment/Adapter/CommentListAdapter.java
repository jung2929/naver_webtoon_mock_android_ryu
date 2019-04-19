package com.example.myfirstapp.WebtoonComment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.ViewHolder.CommentViewHolder;
import com.example.myfirstapp.WebtoonComment.Entities.CommentData;
import com.example.myfirstapp.WebtoonComment.Entities.RequestCommentNoData;
import com.example.myfirstapp.WebtoonComment.Entities.ResponseCommentBehaviorData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends BaseAdapter {
    private ArrayList<CommentData> commentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private String textSpace;
    private boolean isNormalComment;

    public CommentListAdapter(ArrayList<CommentData> commentList, Context context, final int COMMENT_TYPE) {
        this.commentList = commentList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (COMMENT_TYPE == 0) {//BEST
            this.textSpace = "              ";
            this.isNormalComment = false;
        } else {
            this.textSpace = "";
            this.isNormalComment = true;
        }
    }

    public void setData(ArrayList<CommentData> list) {
        this.commentList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (commentList == null) {
            return 0;
        }
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void requestCommentLike(boolean like, final int pos) {
        Call<ResponseCommentBehaviorData> commentbehaviorDataCall;
        RequestCommentNoData commentNoData = new RequestCommentNoData(pos);
        if (like) {
            commentbehaviorDataCall = Singleton.softcomicsService.addLikeComment(commentNoData);
        } else {
            commentbehaviorDataCall = Singleton.softcomicsService.addDislikeComment(commentNoData);
        }
        commentbehaviorDataCall.enqueue(new Callback<ResponseCommentBehaviorData>() {
            @Override
            public void onResponse(Call<ResponseCommentBehaviorData> call, Response<ResponseCommentBehaviorData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://성공
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, call.toString() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommentBehaviorData> call, Throwable t) {
                Toast.makeText(context, call.toString() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setCommentBehavior(final CommentViewHolder holder, final int position) {
        holder.getLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCommentLike(true, position);
            }
        });
        holder.getDislike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCommentLike(false, position);
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentData item = commentList.get(position);
        CommentViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_comment, null);
            holder = new CommentViewHolder();
            holder.setUserId(convertView.findViewById(R.id.item_comment_user_id));
            holder.setDate(convertView.findViewById(R.id.item_comment_date));
            holder.setCommentContent(convertView.findViewById(R.id.item_comment_content));
            holder.setLike(convertView.findViewById(R.id.item_comment_like_text));
            holder.setDislike(convertView.findViewById(R.id.item_comment_dislike_text));
            holder.setBestText(convertView.findViewById(R.id.item_comment_best));

            setCommentBehavior(holder, position);

            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }
        holder.getUserId().setText(item.getUserId());
        holder.getDate().setText(item.getCommentDate());
        holder.getCommentContent().setText(textSpace + item.getCommentContent());
        holder.getLike().setText(item.getCommentLike() + "");
        holder.getDislike().setText(item.getCommentDisLike() + "");
        if (isNormalComment == true) {
            holder.getBestText().setVisibility(View.GONE);
        } else {
            holder.getBestText().setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
