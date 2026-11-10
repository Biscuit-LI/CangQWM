package com.sky.controller.user;


import com.sky.constant.JwtClaimsConstant;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;


    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginVO userLoginVO) {
        log.info("用户端登录");
        User user=userService.login(userLoginVO);

        //JWT
        Map<String,Object> claim=new HashMap<>();
        claim.put(JwtClaimsConstant.USER_ID,user.getId());
        JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claim);

        UserLoginVO userLoginVOne= UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(user.getOpenid())
                .build();

        return Result.success(userLoginVOne);
    }

}
