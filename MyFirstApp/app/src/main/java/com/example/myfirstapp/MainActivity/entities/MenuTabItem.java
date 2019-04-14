package com.example.myfirstapp.MainActivity.entities;

public class MenuTabItem {
    int unClickedImage;
    int clickedImage;
    String text;

    public MenuTabItem(int unClickedImage, int clickedImage, String text) {
        this.unClickedImage = unClickedImage;
        this.clickedImage = clickedImage;
        this.text = text;
    }

    public int getUnClickedImage() {
        return unClickedImage;
    }

    public int getClickedImage() {
        return clickedImage;
    }

    public String getText() {
        return text;
    }
}
