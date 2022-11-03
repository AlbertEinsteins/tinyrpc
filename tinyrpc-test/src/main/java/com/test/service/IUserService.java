package com.test.service;

import com.test.entity.User;

public interface IUserService {
    User findByUid(Integer uid);

}
