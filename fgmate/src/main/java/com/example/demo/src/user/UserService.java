package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }


    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public PostKakaoReq callbackKakao(PostKakaoTokenReq postKakaoToken){

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String email = "";
        long id=0;
        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + postKakaoToken.getToken()); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PostKakaoReq(id, email);
    }

    // 카카오 로그인/회원 가입
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public PostKakaoRes connectKakao(PostKakaoReq postKakaoReq) throws BaseException {
        try {
            if (userProvider.checkKakaoUser(postKakaoReq.getId()) == 1) {
                // 존재하면 로그인
                int userId = userDao.logInKakao(postKakaoReq.getId());
                String jwt = jwtService.createJwt(userId);
                String result = "로그인에 성공했습니다.";
                return new PostKakaoRes(userId,jwt, 1);
            } else {

                int userId = userDao.sighUpKakao(postKakaoReq);
                String jwt = jwtService.createJwt(userId);
                String result = "회원가입에 성공했습니다.";
                return new PostKakaoRes(userId,jwt,0);
            }
        } catch (Exception exception) {
            System.out.println("login");
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void createProfile(String name, int userId) throws BaseException {
        try {
            userDao.createProfile(name, userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
