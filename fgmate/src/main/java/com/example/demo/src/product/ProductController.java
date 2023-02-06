package com.example.demo.src.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/products")

public class ProductController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired

    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService) {
        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    //전체 식품 조회
    @ResponseBody
    @GetMapping("/{refriId}")
    public BaseResponse<List<GetProductRes>> getAllProducts(@PathVariable("refriId") int refriId) {
        try {
            List<GetProductRes> getProductRes = productProvider.getAllProducts(refriId);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    //식품 1개 조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetProductRes> getProducts(@RequestParam int productId) {
        try {
            GetProductRes getProductRes = productProvider.getProduct(productId);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //식품 생성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createProduct(@RequestBody PostProductReq postProductReq) {
        try {
            productService.createProduct(postProductReq);
            return new BaseResponse<>("식품 생성 완료");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //식품 수정
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyProduct(@RequestBody PatchProductReq patchProductReq) {
        try {
            productService.modifyProduct(patchProductReq);
            return new BaseResponse<>("정보 수정 완료");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //식품 삭제
    @ResponseBody
    @DeleteMapping("")
    public BaseResponse<String> deleteProduct(@RequestParam int productId) {
        try {
            productService.deleteProduct(productId);
            String result = "선택하신 식품이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //식품에 달린 댓글 전체 조회
    @ResponseBody
    @GetMapping("/comment/{productId}")
    public BaseResponse<List<GetCommentRes>> getAllComments(@PathVariable("productId") int productId) {
        try {
            List<GetCommentRes> getCommentRes = productProvider.getAllComments(productId);
            return new BaseResponse<>(getCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //식품에 댓글 작성하기
    @ResponseBody
    @PostMapping("/comment")
    public BaseResponse<String> writeComment(@RequestBody PostCommentReq postCommentReq) {
        try{
            int userId = jwtService.getUserIdx();
            productService.writeComment(postCommentReq, userId);
            return new BaseResponse<>("댓글 작성 완료");
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
