package test;

import dao.OrderDao;

/**
 * Created by Administrator on 2017/6/11.
 */
public class TestCase {
    public static void main(String[] args){
        OrderDao orderDao=new OrderDao();
        System.out.println(orderDao.findOrderbyUserId(1111114)+"ooo");
    }
}
