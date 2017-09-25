package service.impl;

import dao.OrderDao;
import dao.ProductDao;
import entity.*;
import exception.MsgException;
import service.OrderService;
import service.ProductService;

import java.util.*;

/**
 * Created by Administrator on 2017/6/10.
 */
public class OrderServiceImp implements OrderService {
    private OrderDao orderDao=new OrderDao();
    public void addOrder(HashMap<String, Integer> map, String receiverinfo, int id, double totlMonkey) {
        ProductDao prodDao=new ProductDao();
        ProductService productService=new ProductServiceImp();
        Map<Product, Integer> productIntegerMap = productService.addProdList(map);
        Order order=new Order();
        order.setReceiverinfo(receiverinfo);
        order.setId(UUID.randomUUID().toString());
        order.setOrdertime(new Date());
        order.setPaystate(0);
        order.setUser_id(id);
        order.setMoney(totlMonkey);
        orderDao.addOrder(order);
        List<OrderItem> list=new ArrayList<OrderItem>();
        for (Map.Entry<Product,Integer> entry:productIntegerMap.entrySet()){
            Product prod = prodDao.findProdsById(entry.getKey().getId());
            //3.2如果库存不足，则抛出自定义异常
            if(prod.getPnum()<entry.getValue()){
                throw new MsgException("商品"+prod.getId()+","+prod.getName()
                        +"库存不足，当前库存为："+prod.getPnum());
            }else{
                //3.3库存充足,修改库存，并向orderitem表中添加一条记录
                prodDao.changePnumById(prod.getId(), prod.getPnum()-entry.getValue());
                orderDao.addOrderItem(order.getId(),entry.getKey().getId(),entry.getValue());
            }
        }


    }

    public List<OrderInfo> showOrderList(int id) {
        List<OrderInfo> list=new ArrayList<OrderInfo>();
        List<Order> listOrder=orderDao.findOrderbyUserId(id);
        System.out.println(listOrder+"1");
        for(Order order:listOrder){
            OrderInfo orderInfo=new OrderInfo();
            Map<Product,Integer> map= orderDao.findProdMapById(order.getId());
            orderInfo.setOrder(order);
            orderInfo.setMap(map);
            System.out.println(map+"2");
            list.add(orderInfo);
        }
        return list;
    }

    public void doPay(String r6_Order) {
        Order order = orderDao.findOrderById(r6_Order);
        if(order.getPaystate()==0){
            orderDao.updatePayStateById(r6_Order);//
        }
    }

    public void deleteOrderById(String id) {
        ProductDao productDao=new ProductDao();
        Order order = orderDao.findOrderById(id);
        //1.2该订单不存在时抛出异常
        if(order==null){
            throw new MsgException("该订单不存在！");
        }
        //1.3判断是否已经支付
        if(order.getPaystate()!=0){
            throw new MsgException("只用未支付的订单才能被删除");
        }
        orderDao.deleteOrder(id);
        List<OrderItem> list=orderDao.findOrderitemByOrderId(id);
        if (list!=null) {
            for (OrderItem orderItem : list) {
                productDao.upNumProduct(orderItem.getProduct_id(), orderItem.getBuynum());
            }
            orderDao.deleteOrderItem(id);
        }

    }

    public List<SaleInfo> findAllSaf() {
        return orderDao.findAllSal();
    }
}
