package factory;

import anno.Tran;
import service.Service;
import utils.PropUtil;
import utils.TranManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/5/22.
 */
public class BasicFactory {
    private static BasicFactory basicFactory=new BasicFactory();
    private BasicFactory(){}
    public static BasicFactory getBasicFactory(){
        return basicFactory;
    }
    public <T>T getInstance(Class<T> clz){
        /*
        例如传值是UserDao.class-->UserDao--->dao.UserDao
        clz.getName();--->dao.UserDao
        clz.getSimpleName()--->UserDao
         */
        String implStr= new PropUtil("conf.properties").getKey(clz.getSimpleName());
        try {
            Class aClass = Class.forName(implStr);
            //判断该类是不是Service的子类
            if (Service.class.isAssignableFrom(aClass)) {
                final T t = (T) aClass.newInstance();
                //t----->Proxy
                T proxy = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    /*
                     *控制事物
                     * 记录日志
                     * 细粒度的控制权限
                     */
                        if (method.isAnnotationPresent(Tran.class)) {
                            try {
                                TranManager.startTran();
                                //                     * 调用委托方法
                                Object obj = method.invoke(t, args);
                                TranManager.commitTran();
                                return obj;
                            } catch (InvocationTargetException ite){
                                ite.getTargetException().printStackTrace();
                                throw ite.getTargetException();
                            }catch (Exception e) {
                                TranManager.rollbackTran();
                                throw new RuntimeException(e);
                            } finally {
                                TranManager.release();
                            }
                        } else {
                            try {
                                return method.invoke(t, args);
                            }catch (Exception e) {
                                throw new RuntimeException(e);
                            } finally {
                                TranManager.release();
                            }
                        }
                    }
                });
                return proxy;
            }else {
                return (T) aClass.newInstance();
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载实现类失败",e);
        }
    }
//    public <T>T getInstance(Class<T> clz){
//        /*
//        例如传值是UserDao.class-->UserDao--->dao.UserDao
//        clz.getName();--->dao.UserDao
//        clz.getSimpleName()--->UserDao
//         */
//        String implStr= PropUtil.getKey(clz.getSimpleName());
//        try {
//            Class aClass = Class.forName(implStr);
//            return (T)aClass.newInstance();
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("加载实现类失败",e);
//        }
//    }



    //根据传值  返回对应的实现类的对象  容易写错类名
    public Object getInstance1(String intfName){
        String implStr=new PropUtil("conf.properties").getKey(intfName);
        try {
            Class aClass = Class.forName(implStr);
            return aClass.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载实现类失败",e);
        }
    }


}
