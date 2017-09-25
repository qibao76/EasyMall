package entity;

import java.util.Date;
/*
CREATE TABLE orders (
	  id varchar(100) NOT NULL,   ---订单id
	  money double default NULL,   ---金额
	  receiverinfo varchar(255) default NULL,---收件人信息
	  paystate int(11) default NULL,        ---付款状态  0表示未付款，1表示已付款
	  ordertime timestamp NOT NULL,     --订单的添加时间
	  user_id int(11) default NULL,        ---用户id
	  PRIMARY KEY  (id),   ----定义主键
	  KEY user_id (user_id),    ----  指定外键
  	  CONSTRAINT orders_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
    );
 */
public class Order {
	private String id;
	private double money;
	private String receiverinfo;
	private int paystate;
	private Date ordertime;//?
	private int user_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getReceiverinfo() {
		return receiverinfo;
	}
	public void setReceiverinfo(String receiverinfo) {
		this.receiverinfo = receiverinfo;
	}
	public int getPaystate() {
		return paystate;
	}
	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
