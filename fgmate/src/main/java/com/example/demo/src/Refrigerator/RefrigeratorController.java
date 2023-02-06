package com.example.demo.src.Refrigerator;
import com.example.demo.src.Refrigerator.model.GetUserNameRes;
import com.example.demo.src.Refrigerator.model.PostInviteGroupReq;
import com.example.demo.src.user.model.PatchRefrigeratorReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Refrigerator.model.GetRefrigeratorListRes;
import com.example.demo.src.Refrigerator.model.PostRefrigeratorReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/refris")

public class RefrigeratorController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RefrigeratorProvider refrigeratorProvider;
    @Autowired
    private final RefrigeratorService refrigeratorService;
    @Autowired
    private final JwtService jwtService;

    public RefrigeratorController(RefrigeratorProvider refrigeratorProvider, RefrigeratorService refrigeratorService, JwtService jwtService) {
        this.refrigeratorProvider = refrigeratorProvider;
        this.refrigeratorService = refrigeratorService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/{name}")
    public BaseResponse<String> createRefrigerator(@PathVariable String name) {
        try {
            int userId = jwtService.getUserIdx();
            refrigeratorService.createRefrigerator(new PostRefrigeratorReq(name,userId));
            return new BaseResponse<String>("냉장고 생성 완료");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetRefrigeratorListRes>> createRefrigerator() {
        try {
            int userId = jwtService.getUserIdx();
            return new BaseResponse<>(refrigeratorProvider.getRefrigeratorListResList(userId));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PatchMapping("/{name}/{refriId}")
    public BaseResponse<String> modifyRefrigerator(@PathVariable String name, @PathVariable int refriId) {
                 try {
                     refrigeratorService.modifyRefrigerator(new PatchRefrigeratorReq(name, refriId));
                    return new BaseResponse<>("냉장고 수정 완료");
                 } catch (BaseException exception) {
                    return new BaseResponse<>((exception.getStatus()));
                }
    }

    @ResponseBody
    @DeleteMapping("/{refriId}")
    public BaseResponse<String> deleteRefrigerator(@PathVariable int refriId) {
                 try {
                     refrigeratorService.deleteRefrigerator(refriId);
                    return new BaseResponse<>("냉장고 삭제 완료");
                 } catch (BaseException exception) {
                    return new BaseResponse<>((exception.getStatus()));
                }
    }

    @ResponseBody
    @PostMapping("/{refriId}/{userId}")
    public BaseResponse<String> PostController(@PathVariable int refriId, @PathVariable int userId) {
                 try {
                     refrigeratorService.inviteGroup(new PostInviteGroupReq(refriId, userId));
                    return new BaseResponse<>("가입 완료");
                 } catch (BaseException exception) {
                    return new BaseResponse<>((exception.getStatus()));
                }
    }

    @ResponseBody
    @DeleteMapping("/{refriId}/{userId}")
    public BaseResponse<String> DeleteController(@PathVariable int refriId, @PathVariable int userId) {
                 try {
                     refrigeratorService.deleteGroupUser(new PostInviteGroupReq(refriId,userId));
                    return new BaseResponse<>("탈퇴 완료");
                 } catch (BaseException exception) {
                    return new BaseResponse<>((exception.getStatus()));
                }
    }



}

