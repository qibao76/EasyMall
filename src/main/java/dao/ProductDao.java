package dao;

import entity.Product;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DBUtil;
import utils.ResultSetHandler;
import utils.TranManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/4.
 */
public class ProductDao implements Dao{
    public int save(Product product) {

        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs());
        String sql = "insert into products(id,name,category,pnum,price,description,imgurl) values(?,?,?,?,?,?,?)";

        try {
            int i = queryRunner.update(sql, product.getId(), product.getName(),
                    product.getCategory(), product.getPnum(), product.getPrice(), product.getDescription(), product.getImgurl());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public List<Product> findListProduct() {
        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs());
        String sql = "select * from products";

        try {

            List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
            //  List<Product> list=DBUtil.query(sql,new BeanListHandler<Product>(Product.class));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("后台查询商品列表");
        }

    }

    public int delete(String id) {
        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs());
        String sql = "delete from products where id = ?";
        try {
            int i = queryRunner.update(sql, id);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int upNumProduct(String id, int num) {
        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs());
        String sql = "update products set pnum=? where id =?";
        try {
            int update = queryRunner.update(TranManager.getConn(),sql, num, id);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询所有符合条件的商品数量
     *
     * @param name     商品名称关键字
     * @param category 分类关键字
     * @param min      价格区间
     * @param max
     * @return
     */
    public int getProdsCountByKey(String name, String category, double min, double max) {
        String sql = "select count(id) from products where name like ?" +
                " and category like ? and price>=? and price<=?";
        return DBUtil.query(sql, new ResultSetHandler<Integer>() {
            public Integer handler(ResultSet resultSet) throws Exception {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }, "%" + name + "%", "%" + category + "%", min, max);
    }

    /**
     * @param i        开始哪一行
     * @param rpp      每页显示的多少条
     * @param name
     * @param category
     * @param min
     * @param max
     * @return
     */
    public List<Product> findProdsByKeyLimit(int i, int rpp, String name, String category, double min, double max) {
        //1、编写sql语句
        String sql = "select * from products where name like ? and category like ? " +
                "and price>=? and price<=? limit ?,?";
        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs());
        try {
            List<Product> query = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), "%" + name + "%", "%" + category + "%", min, max, i, rpp);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询商品列表失败");
        }
    }

    public Product findProdsById(String id) {
        String sql = "select * from products where id = ?";
        QueryRunner queryRunner=new QueryRunner(DBUtil.getDs());
        try {
            return queryRunner.query(sql,new BeanHandler<Product>(Product.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询展示商品信息失败");
        }
    }

    public void changePnumById(String id, int i) {
        String sql="update products set pnum=? where id=?";
        QueryRunner queryRunner=new QueryRunner(DBUtil.getDs());
        try {
            queryRunner.update(TranManager.getConn(),sql, i, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改库存失败");
        }
    }


}