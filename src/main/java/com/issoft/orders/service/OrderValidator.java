package com.issoft.orders.service;

import com.issoft.orders.domain.Address;
import com.issoft.orders.domain.Order;
import com.issoft.orders.domain.OrderItem;

import java.math.BigDecimal;

public class OrderValidator {

    public boolean validateOrderToPlace(Order order) {
        if (order == null) {
            return false;
        }
        if (order.getDateOfOrder() == null) {
            return false;
        }
        if (order.getUserId() == null) {
            return false;
        }
        if (order.getStatus() == null) {
            return false;
        }
        if (!validateAddress(order.getAddress())) {
            return false;
        }
        return validateItems(order.getItems());
    }

    private boolean validateAddress(Address address) {
        if (address == null) {
            return false;
        }
        if (address.getCountry() == null) {
            return false;
        }
        if (address.getCity() == null) {
            return false;
        }
        if (address.getStreet() == null) {
            return false;
        }
        Integer houseNumber = address.getHouseNumber();
        if (houseNumber == null || houseNumber < 1) {
            return false;
        }
        Integer building = address.getBuilding();
        return building != null && building >= 1;
    }

    private boolean validateItems(OrderItem[] items) {
        if (items == null) {
            return false;
        }
        for (OrderItem item : items) {
            if (item == null) {
                return false;
            }
            if (item.getName() == null) {
                return false;
            }
            Integer quantity = item.getQuantity();
            if (quantity == null || quantity < 1) {
                return false;
            }
            BigDecimal price = item.getPrice();
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                return false;
            }
        }
        return true;
    }
}
