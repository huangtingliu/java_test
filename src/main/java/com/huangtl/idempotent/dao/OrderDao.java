package com.huangtl.idempotent.dao;

import com.huangtl.idempotent.bean.Order;
import com.huangtl.xiaoxinhuan.entity.Location;
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

    void insertOrderImg(List<Map> list);

//    查询所有老人id
    List<Map> queryMemberList();

    List<Map> queryOldCallInfo();

    void insertCallInfo(List<Map> list);
}
