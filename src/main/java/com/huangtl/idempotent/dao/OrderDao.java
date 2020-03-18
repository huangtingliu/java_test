package com.huangtl.idempotent.dao;

import com.huangtl.idempotent.bean.Order;
import com.huangtl.xiaoxinhuan.entity.Location;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OrderDao {

    List<Map> queryList();

    void updateOrder(Order order);

    int updateOrderByVersion(Order order);

    void updateLocation(Location location);

    List<Map> queryOrderList();

    List<Map> queryOrderImgList();

    void insertOrderImg(List<Map> list);

//    查询所有老人id
    List<Map> queryMemberList();

    List<Map> queryOldCallInfo();

    void insertCallInfo(List<Map> list);

    List<String> queryRandCallIds(@Param("size") int size);
    List<Map> queryRandArchiveIds();
    void updateCallOrg(Map map);



    /*一键通部分*/
    //获取所有老人列表
    List<Map> queryYjtMemberList();
    List<Map> queryYjtMemberRelationList();
    //查询备份的所有通话数据
    List<Map> queryYjtBackupCallList();
    //插入备份数据
    void insertYjtBackupCallList(List<Map> list);

    /*获取老人图片*/
    List<String> queryAllMemberPic();
}
