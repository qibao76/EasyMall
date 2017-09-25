package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
    private Class<T> aClass;
    public BeanHandler(Class<T> aClass) {
        this.aClass=aClass;
    }

    /**
     * 从结果集rs对象获取信息,并封装一个javaBean对象
     * @param resultSet
     * @return
     * @throws Exception
     */
    public T handler(ResultSet resultSet) throws Exception {
        if (resultSet.next()){//存在记录
            //创建javaBean对象
           T t = aClass.newInstance();
           //将class对象aclass转化成BeanInfo对象,可以将属性信息逐一进行封装
            BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd:propertyDescriptors) {
                //获取属性名称
                String name = pd.getName();
                //获取set方法
                Method writeMethod = pd.getWriteMethod();
                //set
                try{
                    writeMethod.invoke(t,resultSet.getObject(name));
                }catch (SQLException e){
                    e.printStackTrace();
                    continue;
                }
            }
            return t;
        }
        return null;
    }

}
