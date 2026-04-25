package com.example.marketingcosmetics.models;

public class Promotion {

    private int id;
    private int campaignId;
    private String title;
    private String description;
    private int discountPercent;
    private String startDate;
    private String endDate;
    private String imageUrl;

    // thêm từ API nâng cấp
    private String campaignTitle;
    private String status; // ACTIVE / ENDED

    // Constructor rỗng (bắt buộc)
    public Promotion() {
    }

    // Constructor đầy đủ
    public Promotion(int id, int campaignId, String title, String description,
                     int discountPercent, String startDate, String endDate,
                     String imageUrl, String campaignTitle, String status) {
        this.id = id;
        this.campaignId = campaignId;
        this.title = title;
        this.description = description;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.campaignTitle = campaignTitle;
        this.status = status;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public void setCampaignTitle(String campaignTitle) {
        this.campaignTitle = campaignTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
