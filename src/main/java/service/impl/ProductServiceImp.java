package service.impl;

import dao.ProductDao;
import entity.Page;
import entity.Product;
import exception.MsgException;
import service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/4.
 */
public class ProductServiceImp implements ProductService {

    ProductDao productDao=new ProductDao();
    public void addProd(Product product) {
        int i = productDao.save(product);
        if (i==0){
            throw new MsgException("添加失败");
        }
    }

    public List<Product> findAllProd() {
        List<Product> list = productDao.findListProduct();
        if (list==null){
            throw new MsgException("查询商品列表失败");
        }
        return  list;
    }

    public void deleteProdById(String id) {
        int row=productDao.delete(id);
        if (row!=1){
            throw new MsgException("删除失败");
        }
    }

    public boolean update(String id, int num) {
        int row=productDao.upNumProduct(id,num);
        return row==1;
    }

    public Page<Product> pageList(int thispage, int rpp, String name, String category, double min, double max) {
        Page<Product> page=new Page<Product>();
        //3、将参数保存到page中
        //3.1查询条件的参数
        page.setName(name);
        page.setCategory(category);
        if(min!=Double.MIN_VALUE){
            page.setMinprice(min);
        }
        if(max!=Double.MAX_VALUE){
            page.setMaxprice(max);
        }
        //3.2分页相关的
        page.setRpp(rpp);
        page.setCpn(thispage);
        page.setCountrow(productDao.getProdsCountByKey(name,category,min,max));
        page.setCountpage((page.getCountrow()%rpp==0?0:1)+page.getCountrow()/rpp);
        page.setNextpage(thispage<page.getCountpage()?thispage+1:thispage);
        page.setPrepage(thispage>1?thispage-1:1);
        page.setList(productDao.findProdsByKeyLimit((thispage-1)*rpp,rpp,
                name,category,min,max));
        //2、返回结果
        return page;
    }

    public Product findProdById(String id) {
        Product product=productDao.findProdsById(id);
        return product;
    }

    public Map<Product, Integer> addProdList(HashMap<String, Integer> pids) {
        Map<Product,Integer> map=new HashMap<Product, Integer>();
        for (Map.Entry<String,Integer> entry:pids.entrySet()) {
            Product product = productDao.findProdsById(entry.getKey());
            if (product==null){
                throw new MsgException("添加订单列表失败");
            }
            map.put(product,entry.getValue());
        }
        return map;
    }

}
