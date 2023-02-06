package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/users")

public class UserController {


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/kakao-login")
    public BaseResponse<PostKakaoRes> kakaoLogin(@RequestBody PostKakaoTokenReq postKakaoToken) {

        try {
            PostKakaoRes postKakaoRes=userService.connectKakao(userService.callbackKakao(postKakaoToken));
            return new BaseResponse<>(postKakaoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PatchMapping("/{name}")
    public BaseResponse<String> PostController(@PathVariable String name) {
                 try {
                     int userId = jwtService.getUserIdx();
                     userService.createProfile(name, userId);
                    return new BaseResponse<>("닉네임 설정 완료");
                 } catch (BaseException exception) {
                    return new BaseResponse<>((exception.getStatus()));
                }
    }


}
