package com.techworld.admin.product;

public class ProductDTO {
    private String name;
    private String mainImage;
    private int oldPrice;
    private int newPrice;

    public ProductDTO(String name, String mainImage, int oldPrice, int newPrice) {
        this.name = name;
        this.mainImage = mainImage;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }
}
