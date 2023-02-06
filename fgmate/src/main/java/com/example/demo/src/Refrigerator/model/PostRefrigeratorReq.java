package com.example.demo.src.Refrigerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostRefrigeratorReq {
    private String refrigeratorName;
    private int userId;
}
