package factory;

import dao.UserDao;
import utils.PropUtil;

/**
 * Created by Administrator on 2017/5/19.
 */
public class UserDaoFactory {
    private static UserDaoFactory userDaoFactory=new UserDaoFactory();

    private UserDaoFactory() {

    }

    public static UserDaoFactory getUserDaoFactory() {
        return userDaoFactory;
    }
    public static UserDao getUserDao(){
//        Properties properties = new Properties();
//        properties.list();
        /*
        编写到此处每次访问都会加载一次效率低*/
        String userDao = new PropUtil("conf.properties").getKey("UserDao");
        try {
            Class<UserDao> aClass = (Class<UserDao>) Class.forName(userDao);
            return aClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("加载失败",e);
        }

    }
}
