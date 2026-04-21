package com.example.marketingcosmetics.models;

public class Campaign {
    private String eyebrow;
    private String title;
    private String btnText;
    private int bgType; // 1=dark, 2=green, 3=lilac
    private String status; // running, upcoming, ended

    public Campaign(String eyebrow, String title, String btnText, int bgType, String status) {
        this.eyebrow = eyebrow;
        this.title = title;
        this.btnText = btnText;
        this.bgType = bgType;
        this.status = status;
    }

    public String getEyebrow() { return eyebrow; }
    public String getTitle() { return title; }
    public String getBtnText() { return btnText; }
    public int getBgType() { return bgType; }
    public String getStatus() { return status; }
}