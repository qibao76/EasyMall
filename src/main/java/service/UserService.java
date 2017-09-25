package service;

import entity.User;
import exception.MsgException;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface UserService extends Service{
    void save(User user)throws MsgException;

    User log(String username, String password) throws MsgException;

    Boolean chick(String username);
}
