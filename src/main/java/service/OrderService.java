package service;

import anno.Tran;
import dao.Dao;
import entity.OrderInfo;
import entity.SaleInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/10.
 */
public interface OrderService extends Service {
    @Tran
    void addOrder(HashMap<String, Integer> map, String receiverinfo, int id, double totlMonkey);

    List<OrderInfo> showOrderList(int id);
    @Tran
    void doPay(String r6_order);
    @Tran
    void deleteOrderById(String id);

    List<SaleInfo> findAllSaf();
}
