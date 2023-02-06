package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PatchCommentReq {
    private int userId;
    private int productId;
    private String comment;
}
