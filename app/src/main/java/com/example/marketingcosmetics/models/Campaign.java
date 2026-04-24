package com.example.marketingcosmetics.models;

public class Campaign {

    private int id;

    private String title;
    private String description;
    private String imageUrl;

    private String startDate;
    private String endDate;
    private String status;

    // người tạo campaign
    private int createdById;
    private String createdByName;

    // stats giống Facebook
    private int likeCount;
    private int commentCount;
    private int shareCount;

    public Campaign() {
    }

    public Campaign(int id, String title, String description, String imageUrl,
                    String startDate, String endDate, String status,
                    int createdById, String createdByName,
                    int likeCount, int commentCount, int shareCount) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }

    // ===== GETTERS =====

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getImageUrl() { return imageUrl; }

    public String getStartDate() { return startDate; }

    public String getEndDate() { return endDate; }

    public String getStatus() { return status; }

    public int getCreatedById() { return createdById; }

    public String getCreatedByName() { return createdByName; }

    public int getLikeCount() { return likeCount; }

    public int getCommentCount() { return commentCount; }

    public int getShareCount() { return shareCount; }

    // ===== SETTERS =====

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public void setStatus(String status) { this.status = status; }

    public void setCreatedById(int createdById) { this.createdById = createdById; }

    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public void setShareCount(int shareCount) { this.shareCount = shareCount; }
}