package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranManager {
	private static ThreadLocal<Connection> tl = new ThreadLocal(){
		protected Connection initialValue() {
			try {
				return DBUtil.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("tl获取连接失败");
			}
		};
	};
	
	private TranManager(){}
	public static Connection getConn(){
		return tl.get();
	}
	public static void startTran(){
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commitTran(){
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollbackTran(){
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void release(){
		try {
			tl.get().close();
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
