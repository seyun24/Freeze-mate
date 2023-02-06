package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;
import java.sql.SQLException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductProvider {
    private final ProductDao productDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao, JwtService jwtService) {
        this.productDao = productDao;
        this.jwtService = jwtService;
    }

    public List<GetProductRes> getAllProducts(int refrigeratorId) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getAllProducts(refrigeratorId);
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductRes getProduct(int productsId) throws BaseException {
        try {
            GetProductRes getProductRes = productDao.getProduct(productsId);
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCommentRes> getAllComments(int productId) throws BaseException {
        try {
            List<GetCommentRes> getCommentRes = productDao.getAllComments(productId);
            return getCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
