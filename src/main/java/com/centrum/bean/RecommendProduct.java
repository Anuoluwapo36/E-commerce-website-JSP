package com.centrum.bean;

public class RecommendProduct {
	private Product product;
	private ProductImages primaryImages;
	
	public RecommendProduct(Product product, ProductImages primaryImages) {
		super();
		this.product = product;
		this.primaryImages = primaryImages;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductImages getPrimaryImages() {
		return primaryImages;
	}

	public void setPrimaryImages(ProductImages primaryImages) {
		this.primaryImages = primaryImages;
	}
	
	
	

}
