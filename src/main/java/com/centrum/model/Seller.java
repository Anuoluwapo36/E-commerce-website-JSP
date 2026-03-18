package com.centrum.model;

import java.sql.Timestamp;

public class Seller {
    private int sellerId;
    private String shopName;
    private String shopSlug;
    private String ownerName;
    private String email;
    private String password;
    private String phone;
    private String verificationStatus = "pending"; 
    private boolean isActive = false; 
    private String bankName;
    private String bankAccountNumber;
    private String shopType;
    private String idDocument;      
    private String businessLicense;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getShopSlug() { return shopSlug; }
    public void setShopSlug(String shopSlug) { this.shopSlug = shopSlug; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }
	
	public String getShopType() {
	
		return shopType;
	
	}
	
	public void setShopType(String shopType) {
	
		this.shopType = shopType;
	
	}
	
	public void setActive(boolean isActive) {
	
		this.isActive = isActive;
	
	}
	
	public String getIdDocument() {
	
		return idDocument;
	
	}
	
	public void setIdDocument(String idDocument) {
	
		this.idDocument = idDocument;
	
	}
	
	public String getBusinessLicense() {
	
		return businessLicense;
	
	}
	
	public void setBusinessLicense(String businessLicense) {
	
		this.businessLicense = businessLicense;
	
	}
	
	public Timestamp getCreatedAt() {
	
		return createdAt;
	
	}
	
	public void setCreatedAt(Timestamp createdAt) {
	
		this.createdAt = createdAt;
	
	}
	
	public Timestamp getUpdatedAt() {
	
		return updatedAt;
	
	}
	
	public void setUpdatedAt(Timestamp updatedAt) {
	
		this.updatedAt = updatedAt;
	
	}
	
    
}
