package service.impl;

import dao.UserDao;
import entity.User;
import exception.MsgException;
import service.UserService;

/**
 * Created by Administrator on 2017/5/19.
 */
public class UserServiceImp implements UserService {

    public void save(User user) {
        UserDao userDao=new UserDao();
   //     System.out.println(user);
        User user1 = userDao.findByName(user.getUsername());
        if (user1!=null){
            throw new MsgException("用户名已存在");
        }
        userDao.save(user);
    }

    public User log(String username, String password) throws MsgException{
        UserDao userDao=new UserDao();
        User user = userDao.findByName(username);
        if (user==null){
            throw new MsgException("用户不存在");
        }
        if (!user.getPassword().equals(password)){
            throw new MsgException("密码错误");
        }
        return user;
    }

    public Boolean chick(String username) {
        UserDao userDao=new UserDao();
        User user = userDao.findByName(username);
        if (user==null){
            return false;
        }
        return true;
    }
}
