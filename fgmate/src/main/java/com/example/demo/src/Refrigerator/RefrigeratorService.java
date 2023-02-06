package com.example.demo.src.Refrigerator;

import com.example.demo.config.BaseException;
import com.example.demo.src.Refrigerator.model.PostInviteGroupReq;
import com.example.demo.src.Refrigerator.model.PostRefrigeratorReq;
import com.example.demo.src.user.model.PatchRefrigeratorReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DUPLICATED_GROUP_USER;

@Service

public class RefrigeratorService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RefrigeratorDao refrigeratorDao;
    private final RefrigeratorProvider refrigeratorProvider;
    private final JwtService jwtService;

    @Autowired
    public RefrigeratorService(RefrigeratorDao refrigeratorDao, RefrigeratorProvider refrigeratorProvider, JwtService jwtService) {
        this.refrigeratorDao = refrigeratorDao;
        this.refrigeratorProvider = refrigeratorProvider;
        this.jwtService = jwtService;
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void createRefrigerator(PostRefrigeratorReq postRefrigeratorReq) throws BaseException {
        try {
            refrigeratorDao.createRefrigerator(postRefrigeratorReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void modifyRefrigerator(PatchRefrigeratorReq patchRefrigeratorReq) throws BaseException {
            try {
                refrigeratorDao.modifyRefrigerator(patchRefrigeratorReq);
            } catch (Exception exception) {
                 throw new BaseException(DATABASE_ERROR);
            }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void deleteRefrigerator(int id) throws BaseException {
            try {
                refrigeratorDao.deleteRefrigerator(id);
            } catch (Exception exception) {
                 throw new BaseException(DATABASE_ERROR);
            }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void inviteGroup(PostInviteGroupReq postInviteGroupReq) throws BaseException {
            if(refrigeratorDao.checkGroupUser(postInviteGroupReq)==1){
                throw new BaseException(DUPLICATED_GROUP_USER);
            }
            try {
                refrigeratorDao.inviteGroup(postInviteGroupReq);
            } catch (Exception exception) {
                 throw new BaseException(DATABASE_ERROR);
            }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void deleteGroupUser(PostInviteGroupReq postInviteGroupReq) throws BaseException {
            try {
                refrigeratorDao.deleteGroupUser(postInviteGroupReq);
            } catch (Exception exception) {
                 throw new BaseException(DATABASE_ERROR);
            }
    }

}

