package com.example.demo.src.user;


import com.example.demo.src.Refrigerator.model.PostRefrigeratorReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository


public class UserDao {



    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    // 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (email, password, name) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);


        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // 이메일 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }


    // User 테이블에 존재하는 전체 유저들의 정보 조회
    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from Users";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")
                        )
        );
    }

    // 해당 name을 갖는 유저들의 정보 조회
    public List<GetUserRes> getUsersByNickname(String nickname) {
        String getUsersByNicknameQuery = "select * from User where nickname =?";
        String getUsersByNicknameParams = nickname;
        return this.jdbcTemplate.query(getUsersByNicknameQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickname"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByNicknameParams);
    }

    // 해당 userId를 갖는 유저조회
    public GetUserRes getUser(int id) {
        String getUserQuery = "select * from UserInfo where id = ?";
        int getUserParams = id;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")),
                getUserParams);
    }

    void modifyTest(PatchUserReq patchUserReq){
        String a="update UserInfo set nickname = ? where id=?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getId(),patchUserReq.getNickname()};
        this.jdbcTemplate.update(a,modifyUserNameParams);
    }


    public int logInKakao(long id) {
        String kakaoLogInQuery = "select userId\n" +
                "from Users\n" +
                "where id = ?";


        return this.jdbcTemplate.queryForObject(kakaoLogInQuery,
                int.class,
                id);
    }

    public int checkKakao(long id) {
        String checkKakaoUserQuery = "select exists(select id\n" +
                "from Users\n" +
                "where id = ?) idExist";

        return this.jdbcTemplate.queryForObject(checkKakaoUserQuery,
                int.class,
                id);
    }


    public int sighUpKakao(PostKakaoReq postKakaoReq) {
        String sighUpKakaoQuery = "INSERT INTO Users (id, email) \n" +
                "VALUES (?,?,?)";
        Object[] sighUpKakaoParams = new Object[]{postKakaoReq.getId(),postKakaoReq.getEmail()};
        this.jdbcTemplate.update(sighUpKakaoQuery, sighUpKakaoParams);

        String lastInsertUserKakaoIdQuery = "select last_insert_id()";
        int userId = this.jdbcTemplate.queryForObject(lastInsertUserKakaoIdQuery, int.class);

        return userId;

    }

    public void createProfile(String name, int userId){
        String createProfileQuery="update Users set name=? where userId=?";
        Object[] createProfileParams = new Object[]{name, userId};
        this.jdbcTemplate.update(createProfileQuery,createProfileParams);
    }
}
