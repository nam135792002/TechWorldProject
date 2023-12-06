package com.techworld.product;

public class ProductDTO {
    private Integer id;
    private String name;

    private String alias;
    private String mainImage;
    private int oldPrice;
    private int newPrice;

    private float discountPercent;

    private float averageReview;
    private int countReview;


    public ProductDTO(Integer id, String name, String alias, String mainImage, int oldPrice, int newPrice, float averageReview, int countReview) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.mainImage = mainImage;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.averageReview = averageReview;
        this.countReview = countReview;
    }

    public ProductDTO(Integer id, String name, String alias, String mainImage, int oldPrice, int newPrice, float discountPercent, float averageReview, int countReview) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.mainImage = mainImage;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discountPercent = discountPercent;
        this.averageReview = averageReview;
        this.countReview = countReview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public float getAverageReview() {
        return averageReview;
    }

    public void setAverageReview(float averageReview) {
        this.averageReview = averageReview;
    }

    public int getCountReview() {
        return countReview;
    }

    public void setCountReview(int countReview) {
        this.countReview = countReview;
    }
}
