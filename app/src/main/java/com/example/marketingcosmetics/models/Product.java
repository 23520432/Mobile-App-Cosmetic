package com.example.marketingcosmetics.models;

public class Product {
    private String name;
    private String brand;
    private String price;
    private String oldPrice;
    private String badge;
    private String bottleName;
    private String emoji;
    private int bgType; // 1=pink, 2=lilac, 3=green, 4=gold
    private String category;

    public Product(String name, String brand, String price, String oldPrice,
                   String badge, String bottleName, String emoji, int bgType, String category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.oldPrice = oldPrice;
        this.badge = badge;
        this.bottleName = bottleName;
        this.emoji = emoji;
        this.bgType = bgType;
        this.category = category;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getPrice() { return price; }
    public String getOldPrice() { return oldPrice; }
    public String getBadge() { return badge; }
    public String getBottleName() { return bottleName; }
    public String getEmoji() { return emoji; }
    public int getBgType() { return bgType; }
    public String getCategory() { return category; }
}