package com.example.demo.src.user.model;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchRefrigeratorReq {
    private String name;
    private int id;
}
