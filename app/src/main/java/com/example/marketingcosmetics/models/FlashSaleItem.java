package com.example.marketingcosmetics.models;

public class FlashSaleItem {
    private String name;
    private String description;
    private String discount;
    private String price;
    private int bgType; // 1=pink, 2=lilac, 3=sage

    public FlashSaleItem(String name, String description, String discount, String price, int bgType) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.bgType = bgType;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDiscount() { return discount; }
    public String getPrice() { return price; }
    public int getBgType() { return bgType; }
}