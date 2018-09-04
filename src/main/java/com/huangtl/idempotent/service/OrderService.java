package com.huangtl.idempotent.service;

import com.huangtl.idempotent.bean.Order;
import com.huangtl.idempotent.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderDao orderDao;

    //public string sendorder(String orderId){
    //    boolean lock = transactionTemplate编程式事务{
    //
    //        //状态机乐观锁 update version=version+1 where version = 0;
		//（基于状态机的乐观锁）更改订单状态，返回更改条数是否等于1
    //        return editNum = orderDao.updateByVersion(order);
    //    }
    //
    //    if(lock){
    //        重复调用。。。
    //        return..
    //    }else{
    //        远程调用第三方...
    //        String flag = restTemplate.invoke(url,orderId);
    //
    //        transactionTemplate编程式事务{
    //            更改订单状态为远程调用结果，
    //            return flag;
    //        }
    //    }
    //}

    /**
     * @Transactional 会占用连接数，而请求第三方响应慢，这段时间内会一直占用一个连接，多少个请求就占用多少个连接，导致其他业务无法执行
     * @param orderId
     * @return
     */
    @Transactional
    public String sendOrder(String orderId){

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(-1);//处理中
        orderDao.updateOrder(order);

        String flag = restTemplate.getForObject("http://127.0.0.1:4000/api/test.do?orderId="+orderId, String.class);

        order.setOrderStatus(0);//已完成
        orderDao.updateOrder(order);

        return flag;
    }

    /**
     * 改为编程式事务，可以有效释放连接，但是无法解决同一个订单的重复请求(无法解决幂等性)
     * @param orderId
     * @return
     */
    public String sendOrder2(String orderId){

        //编程式事务,这段执行完就释放了连接
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                Order order = new Order();
                order.setOrderId(orderId);
                order.setOrderStatus(-1);//处理中
                orderDao.updateOrder(order);
                return null;
            }
        });

        String flag = restTemplate.getForObject("http://127.0.0.1:4000/api/test.do?orderId="+orderId, String.class);

        Order order = new Order();
        order.setOrderId(orderId);
        if("0".equals(flag)){
            order.setOrderStatus(1);//已完成
        }
        orderDao.updateOrder(order);

        return flag;
    }

    /**
     * 悲观锁：select * from table_name for update 锁住表不然其他请求访问
     * 乐观锁：select * from table_name
     * 基于状态机的乐观锁：update table_name set version=1  where version = 0  第一个执行完后，后面的请求就无法满足where条件，则不会重复执行语句
     * 本方法将改为基于状态机的乐观锁
     * @param orderId
     * @return
     */
    public String sendOrder3(String orderId){

        //编程式事务,这段执行完就释放了连接
        boolean lock = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                Order order = new Order();
                order.setOrderId(orderId);
                order.setOrderStatus(-1);//处理中
                return 1==orderDao.updateOrderByVersion(order);
            }
        });

        if(!lock){
            System.out.println("重复提交......");
            return null;
        }

        String flag = restTemplate.getForObject("http://127.0.0.1:4000/api/test.do?orderId="+orderId, String.class);

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                if("0".equals(flag)){ //请求成功
                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setOrderStatus(1);//已完成
                    orderDao.updateOrder(order);
                }
                return null;
            }
        });

        return flag;
    }

    public List queryList() {
        return orderDao.queryList();
    }
}
