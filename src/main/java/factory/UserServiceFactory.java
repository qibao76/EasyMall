package factory;

import service.UserService;
import utils.PropUtil;

public class UserServiceFactory {
	private static UserServiceFactory factory = new UserServiceFactory();

	private UserServiceFactory(){}

	public UserService getIntance(){
		try {
			//读取配置文件, 获取所要创建实例的全路径名
			String className = new PropUtil("conf.properties").getKey("UserService");//UserDao
			//cn.tedu.dao.XmlUserDao

			//根据所获取的类的全路径名创建该类的实例并返回
			Class clz = Class.forName(className);

			return (UserService)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static UserServiceFactory getFactory(){
		return factory;
	}




}
