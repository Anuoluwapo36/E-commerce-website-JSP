package com.centrum.dao;

import java.sql.SQLException;
import java.util.List;
import com.centrum.bean.Product;

public class BannerDaoTest {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        try {
            List<Product> topProducts = productDao.getMostPurchasedWeekly();
            System.out.println("=== DAO Test Results ===");
            System.out.println("Total top products fetched: " + topProducts.size());
            
            for (Product p : topProducts) {
                System.out.println("Product ID: " + p.getProduct_id() + ", Name: " + p.getProduct_name() + ", Image URL: " + p.getImage_url());
            }
        } catch (SQLException e) {
            System.err.println("DAO Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}