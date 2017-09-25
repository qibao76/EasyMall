package utils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * 该类引入了连接池来管理连接，
 * 连接池代替了DriverManager。
 * 它是DBTool的升级版。
 */
public class DBUtil {
	private static ComboPooledDataSource pool=new ComboPooledDataSource();
	private static BasicDataSource ds;

    /**
     * 可以查询单个对象,也可以查询多个对象返回一个list
     * @param sql
     * @param rsh  ResultSetHandler接口的实现类对象
     * @param params  查询对应的参数值
     * @param <T>  对应的javaBean对象或对应的List<javaBean>对象
     * @return
     */
	public static <T> T query(String sql,ResultSetHandler<T> rsh,Object... params){
		Connection connection=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try{
			connection= TranManager.getConn();
			preparedStatement=connection.prepareStatement(sql);
			//为占位符赋值
            for(int i=1;i<=params.length;i++){
                preparedStatement.setObject(i,params[i-1]);
            }
            resultSet = preparedStatement.executeQuery();
            T t = rsh.handler(resultSet);
            return t;
        }catch (SQLException e){
		    e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(connection,preparedStatement,resultSet);
        }
        return null;
    }
    public static int update(String sql,Object... params){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection= TranManager.getConn();;
            preparedStatement=connection.prepareStatement(sql);
            //为占位符赋值
            for(int i=1;i<=params.length;i++){
                preparedStatement.setObject(i,params[i-1]);
            }
            int i = preparedStatement.executeUpdate();
            return i;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(connection,preparedStatement);
        }


    }



//	static {
//		//1.只读取一次连接参数
//		Properties p = new Properties();
//		try {
//			p.load(DBUtil.class.getClassLoader()
//				.getResourceAsStream("db.properties"));
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("读取配置文件失败",e);
//		}
//		String driver = p.getProperty("driver");
//		String url = p.getProperty("url");
//		String user = p.getProperty("user");
//		String pwd = p.getProperty("pwd");
//		String initSize = p.getProperty("initSize");
//		String maxSize = p.getProperty("maxSize");
//		//2.只创建一个连接池
//		ds = new BasicDataSource();
//		//3.将连接参数设置给连接池
//		ds.setDriverClassName(driver);
//		ds.setUrl(url);
//		ds.setUsername(user);
//		ds.setPassword(pwd);
//		ds.setInitialSize(new Integer(initSize));
//		ds.setMaxActive(new Integer(maxSize));
//	}

	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() 
		throws SQLException {
		return pool.getConnection();
	}

	/**
	 * 获取连接池
	 * @return
	 */
	public static DataSource getDs(){
		return pool;
	}
	/**
	 * 由连接池创建的连接，其close()被连接池改为
	 * 归还的作用，而不是真正关闭连接。并且归还时，
	 * 该连接内的数据被清空，状态重置为空闲态。
	 */
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("归还连接失败",e);
			}
		}
	}
	/**
	 * 工具方法: 释放资源
	 * @param conn
	 * @param stat
	 * @param rs
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stat = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
    private static void close(Connection conn, Statement stat) {
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }
}










