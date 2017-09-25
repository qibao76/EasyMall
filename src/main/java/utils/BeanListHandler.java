package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */
public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
    private Class<T> aClass;
    public BeanListHandler(Class<T> aClass) {
        this.aClass=aClass;
    }

    public List<T> handler(ResultSet resultSet) throws Exception {
        List<T> list=new ArrayList<T>();
        while (resultSet.next()){
            //创建javaBean对象
            T t = aClass.newInstance();
            //将结果集封装到bean对象中
            BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
            //获取所有的属性对象
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd:propertyDescriptors){
                //获取属性
                String name = pd.getName();
                //获取set对象
                Method writeMethod = pd.getWriteMethod();
                try{
                    System.out.println(name);
                    writeMethod.invoke(t,resultSet.getObject(name));
                }catch (SQLException e){
                    e.printStackTrace();
                    continue;
                }
            }
            list.add(t);

        }



        return list;
    }
}
