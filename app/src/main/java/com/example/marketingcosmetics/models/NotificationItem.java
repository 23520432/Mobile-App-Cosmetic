package com.example.marketingcosmetics.models;

public class NotificationItem {
    private String emoji;
    private String title;
    private String time;
    private int type; // 1=new, 2=sale, 3=gift

    public NotificationItem(String emoji, String title, String time, int type) {
        this.emoji = emoji;
        this.title = title;
        this.time = time;
        this.type = type;
    }

    public String getEmoji() { return emoji; }
    public String getTitle() { return title; }
    public String getTime() { return time; }
    public int getType() { return type; }
}