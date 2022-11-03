package com.test.service.service;

import com.test.entity.User;
import com.test.service.IUserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {
    @Override
    public User findByUid(Integer uid) {
        return new User(1, "张三");
    }
}
