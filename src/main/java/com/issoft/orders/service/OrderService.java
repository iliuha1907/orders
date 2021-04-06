package com.issoft.orders.service;

import com.issoft.orders.domain.Order;
import com.issoft.orders.domain.OrderItem;
import com.issoft.orders.persistence.OrderItemStorage;
import com.issoft.orders.persistence.OrderStorage;
import com.issoft.orders.persistence.UserStorage;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class OrderService {

    private final OrderValidator orderValidator;
    private final UserStorage userStorage;
    private final OrderStorage orderStorage;
    private final OrderItemStorage orderItemStorage;

    public OrderService(OrderValidator orderValidator, UserStorage userStorage, OrderStorage orderStorage, OrderItemStorage orderItemStorage) {
        this.orderValidator = orderValidator;
        this.userStorage = userStorage;
        this.orderStorage = orderStorage;
        this.orderItemStorage = orderItemStorage;
    }

    public String placeOrder(Order order) {
        if (!orderValidator.validateOrderToPlace(order)) {
            throw new IllegalArgumentException("Order is not valid " + order);
        }
        checkState(userStorage.findByUserId(order.getUserId()).isPresent(), "No such user");
        placeItems(order.getItems());
        final String id = orderStorage.persist(order);
        order.setId(id);
        return id;
    }

    public List<Order> loadAllByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id can not be null");
        }
        checkState(userStorage.findByUserId(userId).isPresent(), "No such user");
        return orderStorage.loadAllByUserId(userId);
    }

    private void placeItems(OrderItem[] orderItems) {
        for (OrderItem item : orderItems) {
            final String itemId = orderItemStorage.persist(item);
            item.setId(itemId);
        }
    }
}
