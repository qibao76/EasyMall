package dao;

import entity.User;
import exception.MsgException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/5/19.
 */
public class UserDao implements Dao{
    /**
     * 注册用户的方法
     * @param user
     * @throws MsgException
     */
    public void save(User user) throws MsgException {
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        QueryRunner queryRunner=new QueryRunner(DBUtil.getDs());
        String sql="INSERT INTO USER (id,username,nickname,password,email)" +
                    " VALUES (null,?,?,?,?)";
        try {
            queryRunner.update(sql,user.getUsername(),user.getNickname(),user.getPassword(),user.getEmail());

//            connection=DBUtil.getConnection();
//            String sql="INSERT INTO USER (id,username,nickname,password,email)" +
//                    " VALUES (null,?,?,?,?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1,user.getUsername());
//            preparedStatement.setString(2,user.getNickname());
//            preparedStatement.setString(3,user.getPassword());
//            preparedStatement.setString(4,user.getEmail());
//            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败",e);
        }finally {
            DBUtil.close(connection,preparedStatement,resultSet);
        }
    }

    /**
     * 根据用户名查询用户
     * @param name
     * @return user
     */
    public User findByName(String  name){
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        QueryRunner queryRunner=new QueryRunner(DBUtil.getDs());
        String sql="select * from user where username=?";
        try {

            return queryRunner.query(sql,new BeanHandler<User>(User.class),name);

//            connection=DBUtil.getConnection();
//            String sql="select * from user where username=?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1,name);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                User user=new User();
//                user.setUsername(resultSet.getString("username"));
//                user.setEmail(resultSet.getString("email"));
//                user.setId(resultSet.getInt("id"));
//                user.setNickname(resultSet.getString("nickname"));
//                user.setPassword(resultSet.getString("password"));
//                return user;
//            }else {
//                return null;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }finally {
            DBUtil.close(connection,preparedStatement,resultSet);
        }

    }

}
