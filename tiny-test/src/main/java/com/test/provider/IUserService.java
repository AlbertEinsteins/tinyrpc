package com.test.provider;

import com.test.entity.User;

public interface IUserService {
    User findByUid(Integer uid);
}
