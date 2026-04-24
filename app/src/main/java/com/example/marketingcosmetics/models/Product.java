package com.example.marketingcosmetics.models;

import java.io.Serializable;

public class Product implements Serializable {

    // ====== DATA từ DATABASE ======
    private int id;
    private String name;
    private String brand;
    private double price;
    private String imageUrl;
    private String description;
    private String ingredients;
    private int categoryId;
    private String buyLink;
    private String createdAt;

    // ====== UI (OPTIONAL) ======
    private String badge;   // HOT, SALE, NEW
    private int bgType;     // 1=pink, 2=lilac, 3=green, 4=gold

    // ====== CONSTRUCTOR ======
    public Product(int id, String name, String brand, double price,
                   String imageUrl, String description, String ingredients,
                   int categoryId, String buyLink, String createdAt) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.ingredients = ingredients;
        this.categoryId = categoryId;
        this.buyLink = buyLink;
        this.createdAt = createdAt;

        // default UI
        this.badge = "";
        this.bgType = 1;
    }

    // ====== GETTER ======
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public String getIngredients() { return ingredients; }
    public int getCategoryId() { return categoryId; }
    public String getBuyLink() { return buyLink; }
    public String getCreatedAt() { return createdAt; }

    // ====== UI GETTER ======
    public String getBadge() { return badge; }
    public int getBgType() { return bgType; }

    // ====== UI SETTER ======
    public void setBadge(String badge) { this.badge = badge; }
    public void setBgType(int bgType) { this.bgType = bgType; }

    // ====== XỬ LÝ INGREDIENTS ======
    public String[] getIngredientList() {
        if (ingredients == null || ingredients.isEmpty()) return new String[0];

        String[] arr = ingredients.split(",");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim(); // bỏ khoảng trắng
        }
        return arr;
    }
}