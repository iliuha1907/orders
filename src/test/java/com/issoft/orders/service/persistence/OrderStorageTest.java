package com.issoft.orders.service.persistence;

import com.issoft.orders.domain.Order;
import com.issoft.orders.persistence.OrderStorage;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.issoft.orders.service.data.OrderTestSamples.anyOrder;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderStorageTest {

    private final OrderStorage orderStorage = new OrderStorage();

    @Test
    public void persist() {
        //given
        Order order = anyOrder();

        //then
        final String id = orderStorage.persist(order);
        assertThat(id, is(not(null)));
    }

    @Test
    public void persist_null() {
        //then
        assertThrows(NullPointerException.class, () -> orderStorage.persist(null));
    }

    @Test
    public void loadAllByUserId() {
        //given
        String userId = "id";
        List<Order> expectedOrders = Collections.singletonList(anyOrder());

        //then
        final List<Order> orders = orderStorage.loadAllByUserId(userId);
        assertThat(orders, is(not(null)));
        assertThat(orders, is(equalTo(expectedOrders)));
    }

    @Test
    public void loadAllByUserId_null() {
        //then
        assertThrows(NullPointerException.class, () -> orderStorage.loadAllByUserId(null));
    }
}
