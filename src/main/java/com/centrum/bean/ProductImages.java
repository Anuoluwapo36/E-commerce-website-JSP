package com.centrum.bean;

public class ProductImages {
	private int productimage_id;
	private int product_Id;
	private String image_url;
	private boolean is_Primary;

	public int getProductimage_id() {
		return productimage_id;
	}

	public void setProductimage_id(int productimage_id) {
		this.productimage_id = productimage_id;
	}

	public int getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(int product_Id) {
		this.product_Id = product_Id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public boolean isIs_Primary() {
		return is_Primary;
	}

	public void setIs_Primary(boolean is_Primary) {
		this.is_Primary = is_Primary;
	}

}
