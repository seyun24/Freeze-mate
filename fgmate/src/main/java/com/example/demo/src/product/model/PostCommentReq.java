package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentReq {
    private int productId;
    private String comment;
}
