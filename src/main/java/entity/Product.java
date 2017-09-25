package entity;


public class Product {
	private String id;
	private String name;
	private double price;
	private String category;
	private int pnum;
	private String imgurl;
	private String description;

	@Override
	public String toString() {
		return "Product{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", category='" + category + '\'' +
				", pnum=" + pnum +
				", imgurl='" + imgurl + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return 31+id==null?0:id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(this==obj){
			return true;
		}
		//obj不为null，和this也不是同一个对象（z在堆内存中不为一个）
		//obj是否是Product类的对象
		if(!(obj instanceof Product)){
			return false;
		}
		Product other = (Product)obj;
		if(id!=null){
			if(id.equals(other.getId())){
				return true;
			}
		}
		return false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
