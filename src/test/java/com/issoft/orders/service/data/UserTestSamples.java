package com.issoft.orders.service.data;

import com.issoft.orders.domain.User;

public class UserTestSamples {

    public static User anyUser(String id) {
        return new User(id, "Ivan", "Ivanov");
    }
}
