package com.example.myfirstapp.entities;

public class WebtoonContentsData {
    //WebtoonListActivity ListView 사용
    private int mItemType;
    private int mThumbnailDrawableId;
    private String mTitle;
    private String mStarPoint;
    private String mDate;
    private boolean isRead;

    public WebtoonContentsData(){

    }
    public WebtoonContentsData(String mTitle, int Thumbnail){
        this.mTitle = mTitle;
        this.mThumbnailDrawableId = Thumbnail;
    }
    public WebtoonContentsData(int mThumbnailDrawableId, String mTitle, String mStarPoint, String mDate, boolean isRead) {
        this.mThumbnailDrawableId = mThumbnailDrawableId;
        this.mTitle = mTitle;
        this.mStarPoint = mStarPoint;
        this.mDate = mDate;
        this.isRead = isRead;
    }
    public int getmThumbnailDrawableId() {
        return mThumbnailDrawableId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmStarPoint() {
        return mStarPoint;
    }

    public String getmDate() {
        return mDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public int getmItemType() {
        return mItemType;
    }

    public void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }




}
