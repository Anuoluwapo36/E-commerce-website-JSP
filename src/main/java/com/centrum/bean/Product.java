package com.centrum.bean;

import java.sql.Timestamp;

public class Product {

	private int product_id;
	private int seller_id;
	private String product_name;
	private String description;
	private double price;
	private int stock_quantity;
	private String category;
	private String image_url;
	private Timestamp createAt;
	private int view_count;
	private int category_id;
	private int subcategory_id;
	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	
	
	
	  
	public int getCategory_id() {
	
		return category_id;
	
	}

	
	public void setCategory_id(int category_id) {
	
		this.category_id = category_id;
	
	}

	
	public int getSubcategory_id() {
	
		return subcategory_id;
	
	}

	
	public void setSubcategory_id(int subcategory_id) {
	
		this.subcategory_id = subcategory_id;
	
	}



	  private Order_items orderItem; 

	    public Order_items getOrderItem() { return orderItem; }
	    public void setOrderItem(Order_items orderItem) { this.orderItem = orderItem; }
	    
	    
	    private ProductImages productImages;
	    public ProductImages getProductImages() { return productImages; }
	    public void setProductImages(ProductImages productImages) { this.productImages = productImages; }

}
