package com.example.demo.src.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentRes {
    private int commentId;
    private int productId;
    private String comment;
    private int userId;
    private String name;
}
