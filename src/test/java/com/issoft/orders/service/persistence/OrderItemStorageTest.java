package com.issoft.orders.service.persistence;

import com.issoft.orders.domain.OrderItem;
import com.issoft.orders.persistence.OrderItemStorage;
import org.junit.jupiter.api.Test;

import static com.issoft.orders.service.data.OrderTestSamples.validOrderWithNotEmptyOrderItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemStorageTest {

    private final OrderItemStorage orderItemStorage = new OrderItemStorage();

    @Test
    public void persist() {
        //given
        OrderItem orderItem = validOrderWithNotEmptyOrderItems("id").getItems()[0];

        //then
        final String id = orderItemStorage.persist(orderItem);
        assertThat(id, is(not(null)));
    }

    @Test
    public void persist_null() {
        //then
        assertThrows(NullPointerException.class, () -> orderItemStorage.persist(null));
    }
}
