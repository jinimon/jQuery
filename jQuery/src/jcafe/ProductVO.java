package jcafe;

public class ProductVO {
	private String itemNo;
	private String item;
	private String category;
	private double price;
	private String link;
	private String content;
	private double likeIt;
	private String alt;
	private String image;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getLikeIt() {
		return likeIt;
	}

	public void setLikeIt(double likeIt) {
		this.likeIt = likeIt;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ProductVO [itemNo=" + itemNo + ", item=" + item + ", category=" + category + ", price=" + price
				+ ", link=" + link + ", content=" + content + ", likeIt=" + likeIt + ", alt=" + alt + ", image=" + image
				+ "]";
	}
}
