package service;

import entity.Page;
import entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/4.
 */
public interface ProductService extends Service{
    void addProd(Product product);
    List<Product> findAllProd();

    void deleteProdById(String id);

    boolean update(String id, int num);

    /**
     * 分页查询符合条件的商品信息
     * @param thispage：当前第几页
     * @param rpp：每一页的行数
     * @param name：商品名称关键字（模糊查询）
     * @param category：商品分类关键字(模糊查询)
     * @param min：价格区间的最小值
     * @param max：价格区间的最大值
     * @return  封装了分页相关信息的Page<Product>对象
     */
    Page<Product> pageList(int thispage, int rpp, String name, String category, double min, double max);

    Product findProdById(String id);

    Map<Product,Integer> addProdList(HashMap<String, Integer> pids);
}
