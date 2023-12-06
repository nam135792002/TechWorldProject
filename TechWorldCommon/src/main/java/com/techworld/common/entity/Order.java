package com.techworld.common.entity;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 45)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 15)
    private String email;

    @Column(name = "address_line", nullable = false, length = 64)
    private String addressLine;

    @Column(nullable = false, length = 45)
    private String province;

    @Column(nullable = false, length = 45)
    private String district;

    @Column(nullable = false, length = 45)
    private String ward;

    private Date orderTime;

    private int total;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedTime ASC")
    private List<OrderTrack> orderTracks = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String note;

    public Order() {

    }

    public Order(Integer id, Date orderTime, int total) {
        this.id = id;
        this.orderTime = orderTime;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderTrack> getOrderTracks() {
        return orderTracks;
    }

    public void setOrderTracks(List<OrderTrack> orderTracks) {
        this.orderTracks = orderTracks;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShippingAddress(){
        return this.fullName + ", " + this.email + ", " + this.phoneNumber + ", "
                + this.addressLine + ", " + this.ward + ", " + this.district + ", "
                + this.province;
    }

    public void copyShippingAddress(Address address) {
        setFullName(address.getFullName());
        setPhoneNumber(address.getPhoneNumber());
        setEmail(address.getEmail());
        setAddressLine(address.getAddressLine());
        setProvince(address.getProvince().getName());
        setDistrict(address.getDistrict().getName());
        setWard(address.getWard().getName());
    }

    public void addTrack(OrderTrack orderTrack){
        this.orderTracks.add(orderTrack);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", paymentMethod=" + paymentMethod +
                ", customer=" + customer.getFullName() +
                '}';
    }

    @Transient
    public String getDestination(){
        return addressLine + ", " + ward + ", " + district + ", " + province;
    }

//    @Transient
//    public String getProductName(){
//        String productNames = "";
//        productNames = "<ul>";
//        for (OrderDetail detail : orderDetails){
//            productNames += "<li>" + detail.getProduct().getShortName() + "</li>";
//        }
//        productNames += "</ul>";
//        return productNames;
//    }

    @Transient
    public String getUpdatedTimeOnForm(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' | 'hh:mm:ss");
        return dateFormat.format(this.orderTime);
    }

    @Transient
    public boolean isReturnRequested(){
        return hasStatus(OrderStatus.RETURN_REQUESTED);
    }

    @Transient
    public boolean isProcessing(){
        return hasStatus(OrderStatus.PROCESSING);
    }

    @Transient
    public boolean isPicked(){
        return hasStatus(OrderStatus.PICKED);
    }

    @Transient
    public boolean isShipping(){
        return hasStatus(OrderStatus.SHIPPING);
    }

    @Transient
    public boolean isDelivered(){
        return hasStatus(OrderStatus.DELIVERED);
    }

    @Transient
    public boolean isReturned(){
        return hasStatus(OrderStatus.RETURNED);
    }

    @Transient
    public boolean isPackaged(){return hasStatus(OrderStatus.PACKAGED);}

    @Transient
    public boolean isCanceled(){return hasStatus(OrderStatus.CANCELED);}

    private boolean hasStatus(OrderStatus orderStatus) {
        for (OrderTrack aTrack : orderTracks){
            if(aTrack.getStatus().equals(orderStatus)){
                return true;
            }
        }
        return false;
    }
}
