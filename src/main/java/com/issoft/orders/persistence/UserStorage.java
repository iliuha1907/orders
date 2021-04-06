package com.issoft.orders.persistence;

import com.issoft.orders.domain.User;

import java.util.Optional;

public class UserStorage {

    public Optional<User> findByUserId(String userId) {
        return Optional.empty();
    }
}
