package dao;

import entity.Order;
import entity.OrderItem;
import entity.Product;
import entity.SaleInfo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DBUtil;
import utils.TranManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/10.
 */
public class OrderDao implements Dao{
    QueryRunner queryRunner=new QueryRunner(DBUtil.getDs());

    public void addOrderItem(String id, String id1, Integer value) {
        //1、编写sql语句
        String sql = "insert into orderitem(order_id,product_id,buynum) values(?,?,?)";
        //2、调用DaoUtils的update方法
        try{
            queryRunner.update(TranManager.getConn(),sql, id, id1, value);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单对应id和数量");
        }
    }

    public void addOrder(Order order) {
        //1、编写sql语句
        String sql = "insert into orders(id,money,paystate,ordertime,user_id,receiverinfo) " +
                "values(?,?,?,?,?,?)";
        try {
            queryRunner.update(TranManager.getConn(),sql, order.getId(),order.getMoney(),order.getPaystate(),
                    order.getOrdertime(),order.getUser_id(),order.getReceiverinfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findOrderbyUserId(int id) {
        String sql="select * from orders where user_id=?";
        try {
            List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),id);
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单列表失败");
        }
    }

    public Map<Product,Integer> findProdMapById(String id) {
        //1、编写sql语句
        String sql = "SELECT oi.* FROM orderitem  oi, orders od" +
                " WHERE oi.order_id = od.id"+
                " AND oi.order_id = ?";
        try {
            OrderItem orderItem = queryRunner.query(sql, new BeanHandler<OrderItem>(OrderItem.class), id);
            ProductDao productDao=new ProductDao();
            Product product = productDao.findProdsById(orderItem.getProduct_id());
            Map<Product,Integer> map=new HashMap<Product, Integer>();
            map.put(product,orderItem.getBuynum());
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单数量信息失败");
        }
    }

    public Order findOrderById(String r6_order) {
        String sql="select * from orders where id=?";
        try {
             return queryRunner.query(sql, new BeanHandler<Order>(Order.class),r6_order);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单列表失败");
        }
    }

    public void updatePayStateById(String r6_order) {

        String sql = "update orders set paystate=? where id=?";
        try {
            queryRunner.update(TranManager.getConn(), sql, 1, r6_order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String id) {
        String sql = "delete from orders where id = ?";
        try {
            queryRunner.update(TranManager.getConn(),sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除订单失败");
        }
    }

    public List<OrderItem> findOrderitemByOrderId(String id) {
        String sql = "select * from orderitem where order_id=?";
        try {
            List<OrderItem> query = queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class));
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单项失败");
        }
    }

    public void deleteOrderItem(String id) {
        String sql = "delete from orderitem where order_id =?";
        try {
            queryRunner.update(TranManager.getConn(),sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除订单项失败");
        }
    }

    public List<SaleInfo> findAllSal() {
        String sql = " SELECT prod.id prod_id,prod.name prod_name,sum(oi.buynum) sale_num"+
                " from products prod,orderitem oi,orders od"+
                " where prod.id=oi.product_id"+
                " and oi.order_id = od.id"+
                " and od.paystate=1"+
                " group by prod_id"+
                " order by sale_num desc";
        try {
            return queryRunner.query(sql,new BeanListHandler<SaleInfo>(SaleInfo.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询榜单失败");
        }

    }
}
