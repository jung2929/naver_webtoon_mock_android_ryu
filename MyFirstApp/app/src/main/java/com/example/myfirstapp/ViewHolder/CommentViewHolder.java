package com.example.myfirstapp.ViewHolder;

import android.widget.LinearLayout;
import android.widget.TextView;


public class CommentViewHolder {
    private TextView userId;
    private TextView date;
    private TextView commentContent;
    private TextView like;
    private TextView dislike;
    private TextView bestText;
    private TextView deleteButton;
    private LinearLayout llLike;
    private LinearLayout llDislike;

    public LinearLayout getLlLike() {
        return llLike;
    }

    public void setLlLike(LinearLayout llLike) {
        this.llLike = llLike;
    }

    public LinearLayout getLlDislike() {
        return llDislike;
    }

    public void setLlDislike(LinearLayout llDislike) {
        this.llDislike = llDislike;
    }

    public TextView getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(TextView deleteButton) {
        this.deleteButton = deleteButton;
    }

    public TextView getBestText() {
        return bestText;
    }

    public void setBestText(TextView bestText) {
        this.bestText = bestText;
    }

    public TextView getUserId() {
        return userId;
    }

    public void setUserId(TextView userId) {
        this.userId = userId;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(TextView commentContent) {
        this.commentContent = commentContent;
    }

    public TextView getLike() {
        return like;
    }

    public void setLike(TextView like) {
        this.like = like;
    }

    public TextView getDislike() {
        return dislike;
    }

    public void setDislike(TextView dislike) {
        this.dislike = dislike;
    }
}
