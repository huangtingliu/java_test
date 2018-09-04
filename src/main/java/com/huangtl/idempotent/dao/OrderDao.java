package com.huangtl.idempotent.dao;

import com.huangtl.idempotent.bean.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OrderDao {

    List<Map> queryList();

    void updateOrder(Order order);

    int updateOrderByVersion(Order order);
}
