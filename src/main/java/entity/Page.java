package entity;

import java.util.List;

public class Page<T> {
	private int rpp;//每一页显示多少行：rowperpage
	//============分页必须存在============
	private int cpn;//当前第几页:cpn
	private int prepage;//上一页:prepage
	private int nextpage;//下一页:nextpage
	private int countrow;//共多少行:countrow
	private int countpage;//共多少页:countpage
	private List<T>  list;//当前页显示信息对象的集合
	//============分页中可能存在============
	private String name ;//商品名称
	private String category;//商品分类
	private Double minprice;//价格区间的最小值
	private Double maxprice;//价格区间的最大值
	public int getRpp() {
		return rpp;
	}
	public void setRpp(int rpp) {
		this.rpp = rpp;
	}
	public int getCpn() {
		return cpn;
	}
	public void setCpn(int cpn) {
		this.cpn = cpn;
	}
	public int getPrepage() {
		return prepage;
	}
	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}
	public int getNextpage() {
		return nextpage;
	}
	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}
	public int getCountrow() {
		return countrow;
	}
	public void setCountrow(int countrow) {
		this.countrow = countrow;
	}
	public int getCountpage() {
		return countpage;
	}
	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getMinprice() {
		return minprice;
	}
	public void setMinprice(Double minprice) {
		this.minprice = minprice;
	}
	public Double getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(Double maxprice) {
		this.maxprice = maxprice;
	}

}
