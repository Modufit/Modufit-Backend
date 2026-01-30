package com.modufit.users.service.impl;

import com.modufit.common.exception.business.UserExceptions;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import com.modufit.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(userId));
    }
}
