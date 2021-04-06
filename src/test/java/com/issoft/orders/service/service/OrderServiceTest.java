package com.issoft.orders.service.service;

import com.issoft.orders.domain.Order;
import com.issoft.orders.domain.OrderItem;
import com.issoft.orders.persistence.OrderItemStorage;
import com.issoft.orders.persistence.OrderStorage;
import com.issoft.orders.persistence.UserStorage;
import com.issoft.orders.service.OrderService;
import com.issoft.orders.service.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.issoft.orders.service.data.OrderTestSamples.anyOrder;
import static com.issoft.orders.service.data.OrderTestSamples.validOrderWithNotEmptyOrderItems;
import static com.issoft.orders.service.data.UserTestSamples.anyUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    @Mock
    private OrderStorage orderStorage;
    @Mock
    private OrderItemStorage orderItemStorage;
    @Mock
    private UserStorage userStorage;
    @Mock
    private OrderValidator orderValidator;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);

        when(orderStorage.persist(any())).thenReturn(UUID.randomUUID().toString());
        when(orderItemStorage.persist(any())).thenReturn(UUID.randomUUID().toString());

        orderService = new OrderService(orderValidator, userStorage, orderStorage, orderItemStorage);
    }

    @Test
    void placeOrder() {
        //given
        String userId = "10k";
        Order order = validOrderWithNotEmptyOrderItems(userId);

        //when
        when(userStorage.findByUserId(userId)).thenReturn(Optional.of(anyUser(userId)));
        when(orderValidator.validateOrderToPlace(order)).thenReturn(true);

        //then
        final String id = orderService.placeOrder(order);
        assertNotNull(id);
        verify(orderStorage).persist(order);
        verifyPersistCalledForAllItems(orderItemStorage, order.getItems());
        assertThat(order.getId(), is(id));
    }

    @Test
    void placeOrder_invalid() {
        //given
        Order order = anyOrder();

        //when
        when(orderValidator.validateOrderToPlace(order)).thenReturn(false);

        //then
        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(order));
        verify(orderStorage, never()).persist(any());
        verify(orderItemStorage, never()).persist(any());
    }

    @Test
    void placeOrder_noUser() {
        //given
        String userId = "10a";
        Order order = validOrderWithNotEmptyOrderItems(userId);

        //when
        when(orderValidator.validateOrderToPlace(order)).thenReturn(true);
        when(userStorage.findByUserId(userId)).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalStateException.class, () -> orderService.placeOrder(order));
        verify(orderStorage, never()).persist(any());
        verify(orderItemStorage, never()).persist(any());
    }

    @Test
    void loadAllByUserId() {
        //given
        String userId = "20gh";

        //when
        when(userStorage.findByUserId(userId)).thenReturn(Optional.of(anyUser(userId)));
        when(orderStorage.loadAllByUserId(userId)).thenReturn(Collections.emptyList());

        //then
        final List<Order> orders = orderService.loadAllByUserId(userId);
        assertNotNull(orders);
        assertThat(orders, is(Collections.emptyList()));
    }

    @Test
    void loadAllByUserId_noUser() {
        //given
        String userId = "100l";

        //when
        when(userStorage.findByUserId(userId)).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalStateException.class, () -> orderService.loadAllByUserId(userId));
    }

    @Test
    void loadAllByUserId_null() {
        //then
        assertThrows(IllegalArgumentException.class, () -> orderService.loadAllByUserId(null));
    }

    private void verifyPersistCalledForAllItems(OrderItemStorage orderItemStorage, OrderItem[] items) {
        for (OrderItem item : items) {
            verify(orderItemStorage).persist(item);
        }
    }
}
