package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class ProductDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetProductRes> getAllProducts(int refrigeratorId) {
        String getAllProductsQuery = "select * from Products where refrigeratorId = ?";
        int getAllProductsByRefrigeratorIdsParams = refrigeratorId;

        return this.jdbcTemplate.query(getAllProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productImg"),
                        rs.getString("date"),
                        rs.getString("description")),
                getAllProductsByRefrigeratorIdsParams);
    }


    public GetProductRes getProduct(int productsId) {
        String getProductsQuery = "select * from Products where productId = ?";
        int getProductsByProductsIdsParams = productsId;

        return this.jdbcTemplate.queryForObject(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productImg"),
                        rs.getString("date"),
                        rs.getString("description")),
                getProductsByProductsIdsParams);
    }

    public void createProduct(PostProductReq postProductReq) {
        String createProductQuery = "insert into Products (productName, productImg, date, description, refrigeratorId) VALUES (?,?,?,?,?)";
        Object[] createProductParams = new Object[]{postProductReq.getProductName(), postProductReq.getProductImg(),
                postProductReq.getDate(), postProductReq.getDescription(), postProductReq.getRefrigeratorId()};
        this.jdbcTemplate.update(createProductQuery, createProductParams);
    }

    public int modifyProduct(PatchProductReq patchProductReq) {
        String modifyProductNameQuery = "update Products set productName = ?, productImg = ?, date = ?, description = ? where productId = ?";
        Object[] modifyProductNameParams = new Object[]{patchProductReq.getProductName(), patchProductReq.getProductImg(),
                patchProductReq.getDate(), patchProductReq.getDescription(),patchProductReq.getProductId()};

        return this.jdbcTemplate.update(modifyProductNameQuery, modifyProductNameParams);

    }

    public int deleteProduct(int productId) {
        String deleteProductQuery = "delete from Products where productId = ?";
        int deleteProductFromProductsIdsParams = productId;

        return this.jdbcTemplate.update(deleteProductQuery, deleteProductFromProductsIdsParams);
    }

    public List<GetCommentRes> getAllComments(int productId) {
        String getAllProductsQuery = "select commentId, productId, comment,U.userId, name from Comments\n" +
                "inner join Users U on Comments.userId = U.userId  where productId=?";
        int getAllProductsByRefrigeratorIdsParams = productId;

        return this.jdbcTemplate.query(getAllProductsQuery,
                (rs, rowNum) -> new GetCommentRes(
                        rs.getInt("commentId"),
                        rs.getInt("productId"),
                        rs.getString("comment"),
                        rs.getInt("userId"),
                        rs.getString("name")
                        ),
                getAllProductsByRefrigeratorIdsParams);
    }

    public void writeComment(PostCommentReq postCommentReq, int userId) {
        String wrtieCommentQuery = "insert into Comments (productId, userId, comment) VALUES (?,?,?)";
        Object[] writeCommentParams = new Object[]{postCommentReq.getProductId(), userId, postCommentReq.getComment()};
        this.jdbcTemplate.update(wrtieCommentQuery, writeCommentParams);
    }
}
