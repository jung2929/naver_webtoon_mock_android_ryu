package com.example.myfirstapp.WebtoonComment.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentData {

    @SerializedName("User_Id")
    @Expose
    private String userId;
    @SerializedName("Comment_No")
    @Expose
    private Integer commentNo;
    @SerializedName("Comment_Content")
    @Expose
    private String commentContent;
    @SerializedName("Comment_Like")
    @Expose
    private Integer commentLike;
    @SerializedName("Comment_DisLike")
    @Expose
    private Integer commentDisLike;
    @SerializedName("Comment_Date")
    @Expose
    private String commentDate;

    public String getUserId() {
        return userId;
    }

    public Integer getCommentNo() {
        return commentNo;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Integer getCommentLike() {
        return commentLike;
    }

    public Integer getCommentDisLike() {
        return commentDisLike;
    }

    public String getCommentDate() {
        return commentDate;
    }
}
