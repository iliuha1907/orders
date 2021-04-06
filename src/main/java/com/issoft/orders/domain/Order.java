package com.issoft.orders.domain;

import java.util.Date;

public class Order {

    private final Date dateOfOrder;
    private final String userId;
    private String id;
    private OrderStatus status;
    private Address address;
    private OrderItem[] items;

    public Order(Date dateOfOrder, String userId, OrderStatus status, Address address, OrderItem[] items) {
        this.dateOfOrder = dateOfOrder;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }
}
