package com.example.demo.src.Refrigerator;

import com.example.demo.config.BaseException;
import com.example.demo.src.Refrigerator.model.GetRefrigeratorListRes;
import com.example.demo.src.Refrigerator.model.GetUserNameRes;
import com.example.demo.src.Refrigerator.model.PostInviteGroupReq;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service

public class RefrigeratorProvider {

    private final RefrigeratorDao refrigeratorDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RefrigeratorProvider(RefrigeratorDao refrigeratorDao, JwtService jwtService) {
        this.refrigeratorDao = refrigeratorDao;
        this.jwtService = jwtService;
    }

    public List<GetRefrigeratorListRes> getRefrigeratorListResList(int userId) throws BaseException {
        try {
            return refrigeratorDao.getRefrigeratorList(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
