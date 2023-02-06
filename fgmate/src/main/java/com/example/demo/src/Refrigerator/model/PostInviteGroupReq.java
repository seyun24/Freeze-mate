package com.example.demo.src.Refrigerator.model;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostInviteGroupReq {
    private int refriId;
    private int userId;
}
