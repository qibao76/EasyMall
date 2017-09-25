package entity;
/*
 CREATE TABLE orderitem (
	  order_id varchar(100) NOT NULL default '',  ---所属订单的id
	  product_id varchar(100) NOT NULL default '',  ----购买商品的id
	  buynum int(11) default NULL,                ---购买的数量
	  PRIMARY KEY  (order_id,product_id),   ---- 复合主键
	  CONSTRAINT orderitem_ibfk_1 FOREIGN KEY (order_id) REFERENCES orders (id),
	  CONSTRAINT orderitem_ibfk_2 FOREIGN KEY (product_id) REFERENCES products (id)
	) ;
 */
public class OrderItem {
	private String order_id;
	private String product_id;
	private int buynum;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getBuynum() {
		return buynum;
	}
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
	
}
