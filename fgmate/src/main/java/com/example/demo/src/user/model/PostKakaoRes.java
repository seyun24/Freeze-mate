package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostKakaoRes {
    private int userId;
    private String jwt;
    private int loginInfo;
}
