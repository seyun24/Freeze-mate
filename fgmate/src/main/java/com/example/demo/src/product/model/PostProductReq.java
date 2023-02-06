package com.example.demo.src.product.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class PostProductReq {
    private String productName;
    private String productImg;
    private String date;
    private String description;
    private int refrigeratorId;
}
