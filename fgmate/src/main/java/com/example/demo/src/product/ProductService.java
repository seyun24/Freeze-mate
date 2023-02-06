package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final ProductDao productDao;
    private final ProductProvider productProvider;
    private final JwtService jwtService;

    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider, JwtService jwtService) {
        this.productDao = productDao;
        this.productProvider = productProvider;
        this.jwtService = jwtService;
    }

    public void createProduct(PostProductReq postProductReq) throws BaseException{
        try {
            productDao.createProduct(postProductReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyProduct(PatchProductReq patchProductReq) throws BaseException{
        try {
            //프론트와 합의가 필요
//            GetProductRes getProductRes = productDao.getProduct(patchProductReq.getProductId());
//            if (patchProductReq.getProductName() == null) patchProductReq.setProductName(getProductRes.getProductName());
//            if (patchProductReq.getProductImg() == null) patchProductReq.setProductImg(getProductRes.getProductImg());
//            if (patchProductReq.getDate() == null) patchProductReq.setDate(getProductRes.getDate());
//            if (patchProductReq.getDescription() == null) patchProductReq.setDescription(getProductRes.getDescription());

            int successCode = productDao.modifyProduct(patchProductReq);
            if (successCode == 0) {
                throw new BaseException(MODIFY_FAIL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void deleteProduct(int productId) throws BaseException{
        try{
            int successCode = productDao.deleteProduct(productId);
            if (successCode == 0) {
                throw new BaseException(DELETE_FAIL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void writeComment(PostCommentReq postCommentReq, int userId) throws BaseException {
        try {
            productDao.writeComment(postCommentReq, userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
