package com.example.demo.src.Refrigerator;

import com.example.demo.src.Refrigerator.model.GetRefrigeratorListRes;
import com.example.demo.src.Refrigerator.model.GetUserNameRes;
import com.example.demo.src.Refrigerator.model.PostInviteGroupReq;
import com.example.demo.src.Refrigerator.model.PostRefrigeratorReq;
import com.example.demo.src.user.model.PatchRefrigeratorReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class RefrigeratorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //냉장고 생성
    public void createRefrigerator(PostRefrigeratorReq postRefrigeratorReq){
        String createRefrigeratorQuery="insert into Refrigerators(refrigeratorName) value(?)";
        this.jdbcTemplate.update(createRefrigeratorQuery, postRefrigeratorReq.getRefrigeratorName());

        String getIdQuery="select last_insert_id()";
        int refrigeratorId = this.jdbcTemplate.queryForObject(getIdQuery, int.class);

        String createGroupQuery="insert into RefrigeratorGroups(refrigeratorId, userId) values(?,?)";
        Object[] createGroupParams = new Object[]{refrigeratorId,postRefrigeratorReq.getUserId()};
        this.jdbcTemplate.update(createGroupQuery,createGroupParams);
    }

    //냉장고 중복 체크
    public int checkRefrigeratorName(PostRefrigeratorReq postRefrigeratorReq){
        String checkNameQuery="select exists(select refrigeratorName from Refrigerators\n" +
                "    inner join RefrigeratorGroups RG on Refrigerators.refrigeratorId = RG.refrigeratorId\n" +
                "    where userId=? AND refrigeratorName=?) nameExist";
        Object[] checkNameParams = new Object[]{postRefrigeratorReq.getUserId(),postRefrigeratorReq.getRefrigeratorName()};
        return this.jdbcTemplate.queryForObject(checkNameQuery, int.class, checkNameParams);
    }

    //해당 유저의 냉장고 조회
    public List<GetRefrigeratorListRes> getRefrigeratorList(int userId){
        String getRefrigeratorQuery="select refrigeratorName, Refrigerators.refrigeratorId from Refrigerators #냉장고 리스트 조회\n" +
                "inner join RefrigeratorGroups RG on Refrigerators.refrigeratorId = RG.refrigeratorId\n" +
                "where RG.userId =?";
        return this.jdbcTemplate.query(getRefrigeratorQuery,
                (rs, rowNum) -> new GetRefrigeratorListRes(
                        rs.getString("refrigeratorName"),
                        rs.getInt("refrigeratorId")
                ),
                userId
        );
    }

    //냉장고 정보 수정
    public void modifyRefrigerator(PatchRefrigeratorReq patchRefrigeratorReq){
            String modifyRefirgeratorQuery="update Refrigerators set refrigeratorName=? where refrigeratorId=?";
            Object[] modifyRefirgeratorparmas = new Object[]{patchRefrigeratorReq.getName(), patchRefrigeratorReq.getId()};
            this.jdbcTemplate.update(modifyRefirgeratorQuery,modifyRefirgeratorparmas);
    }

    //냉장고 삭제
    public void deleteRefrigerator(int id){
            String deleteQuery="delete from Refrigerators where refrigeratorId=?";
            this.jdbcTemplate.update(deleteQuery,id);
    }

    //냉장고 그룹 초대
    public void inviteGroup(PostInviteGroupReq postInviteGroupReq){
            String inviteGroupQuery="insert into RefrigeratorGroups(refrigeratorId, userId) values(?,?)";
            Object[] inviteGroupParmas = new Object[]{postInviteGroupReq.getRefriId(),postInviteGroupReq.getUserId()};
            this.jdbcTemplate.update(inviteGroupQuery,inviteGroupParmas);
    }

    public int checkGroupUser(PostInviteGroupReq postInviteGroupReq) {

        String checkGroupUserQuery = "select exists(select userId from RefrigeratorGroups where userId = ? and refrigeratorId = ?) idExist";
        Object[] checkGroupUserParmas = new Object[]{postInviteGroupReq.getUserId(),postInviteGroupReq.getRefriId()};
        return this.jdbcTemplate.queryForObject(checkGroupUserQuery,
                int.class,
                checkGroupUserParmas);
    }

    public void deleteGroupUser(PostInviteGroupReq postInviteGroupReq){
            String deleteGroupUserQuery="delete from RefrigeratorGroups where refrigeratorId=? and userId=?";
            Object[] deleteGroupUserparmas = new Object[]{postInviteGroupReq.getRefriId(),postInviteGroupReq.getUserId()};
            this.jdbcTemplate.update(deleteGroupUserQuery,deleteGroupUserparmas);
        }
}

