package com.modufit.users.service;

import com.modufit.users.entity.User;

public interface UserService {
    User getUserById(Long userId);
    User getUserWithProfile(Long userId);
}
