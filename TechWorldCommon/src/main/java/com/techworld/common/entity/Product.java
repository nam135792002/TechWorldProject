package com.techworld.common.entity;

import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 256, nullable = false)
    private String name;

    @Column(unique = true, length = 256, nullable = false)
    private String alias;
    @Column(nullable = false, name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(nullable = false, name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;
    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(name = "old_price", nullable = false)
    private int oldPrice;

    @Column(name = "new_price", nullable = false)
    private int newPrice;

    @Column(name = "discount_percent")
    private float discountPercent;
    private float length;
    private float width;
    private float height;
    private float weight;

    @Column(name = "main_image",nullable = false)
    private String mainImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> listCarts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> listOrderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> listQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> listReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> listWishLists = new ArrayList<>();

    private int reviewCount;
    private float averageRating;

    @Transient private boolean customerCanReview;
    @Transient private boolean reviewByCustomer;

    public Product(Integer id) {
        this.id = id;
    }

    public Product() {

    }

    public Product(String name) {
        this.name = name;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }

    public void addExtraImage(String imageName, String imageUrl){
        this.images.add(new ProductImage(imageName, imageUrl , this));
    }

    public List<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ProductDetail> details) {
        this.details = details;
    }

    public void addDetail(String name, String value){
        this.details.add(new ProductDetail(id,name,value,this));
    }

    public void addDetail(Integer id, String name, String value){
        this.details.add(new ProductDetail(name,value,this));
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String changeExtraImage(String fileUrl, String fileName){
        Iterator<ProductImage> iterator = images.iterator();
        String oldUrl = "";
        while (iterator.hasNext()) {
            ProductImage image = iterator.next();
            if (image.getName().equals(fileName)) {
                oldUrl = image.getUrl();
                image.setUrl(fileUrl);
                return oldUrl;
            }
        }
        return null;
    }

    public boolean containsImageName(String fileName) {
        for (ProductImage image : images) {
            if (image.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    @Transient
    public String getShortName(){
        if(name.length() > 40){
            return name.substring(0,39).concat("...");
        }
        return name;
    }

    @Transient
    public int getDiscountUpdate(){
        float discount = this.discountPercent * 100;
        return (int) discount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isCustomerCanReview() {
        return customerCanReview;
    }

    public void setCustomerCanReview(boolean customerCanReview) {
        this.customerCanReview = customerCanReview;
    }

    public boolean isReviewByCustomer() {
        return reviewByCustomer;
    }

    public void setReviewByCustomer(boolean reviewByCustomer) {
        this.reviewByCustomer = reviewByCustomer;
    }

    public List<CartItem> getListCarts() {
        return listCarts;
    }

    public void setListCarts(List<CartItem> listCarts) {
        this.listCarts = listCarts;
    }

    public List<OrderDetail> getListOrderDetails() {
        return listOrderDetails;
    }

    public void setListOrderDetails(List<OrderDetail> listOrderDetails) {
        this.listOrderDetails = listOrderDetails;
    }

    public List<Question> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public List<Review> getListReviews() {
        return listReviews;
    }

    public void setListReviews(List<Review> listReviews) {
        this.listReviews = listReviews;
    }

    public List<Wishlist> getListWishLists() {
        return listWishLists;
    }

    public void setListWishLists(List<Wishlist> listWishLists) {
        this.listWishLists = listWishLists;
    }
}
