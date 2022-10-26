package com.test.provider;

import com.test.entity.User;

public class UserServiceImpl implements IUserService {
    @Override
    public User findByUid(Integer uid) {
        return new User(1, "张三");
    }
}
