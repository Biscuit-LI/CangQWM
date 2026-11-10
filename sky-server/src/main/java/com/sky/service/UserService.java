package com.sky.service;

import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

public interface UserService {


    User login(UserLoginVO userLoginVO);

}
