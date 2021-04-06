package com.issoft.orders.service.data;

import com.issoft.orders.domain.Address;
import com.issoft.orders.domain.Order;
import com.issoft.orders.domain.OrderItem;
import com.issoft.orders.domain.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

public class OrderTestSamples {

    public static Order validOrderWithNotEmptyOrderItems(String userId) {
        return new Order(new Date(), userId, OrderStatus.ACCEPTED,
                new Address("BLR", "Minsk", "Hamarnika", 1, null),
                new OrderItem[]{new OrderItem("Cap", 2, BigDecimal.TEN)});
    }

    public static Order anyOrder() {
        return anyValidOrder();
    }

    private static Order anyValidOrder() {
        return validOrderWithNotEmptyOrderItems("1");
    }
}
