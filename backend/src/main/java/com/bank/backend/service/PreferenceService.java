package com.bank.backend.service;

import com.bank.backend.dao.PreferenceDao;
import com.bank.backend.dao.ProductDao;
import com.bank.backend.request.PreferenceReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceDao preferenceDao;

    @Autowired
    private ProductDao productDao;

    public List<Map<String, Object>> getAllPreferences(String account) {
        return preferenceDao.getAllPreferences(account);
    }
    
    public List<Map<String, Object>> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public void addPreference(PreferenceReq req) {
        Map<String, Object> product = productDao.getProduct(req.getProductNo());
        if (product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        int price = ((Number) product.get("price")).intValue();
        float feeRate = ((Number) product.get("fee_rate")).floatValue();

        int totalAmount = price * req.getPurchaseQuantity();
        int totalFee = Math.round(totalAmount * feeRate);

        preferenceDao.addPreference(req.getProductNo(), req.getPurchaseQuantity(),
            req.getAccount(), totalFee, totalAmount);
    }

    @Transactional
    public void updatePreference(PreferenceReq req) {
        Map<String, Object> product = productDao.getProduct(req.getProductNo());
        if (product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        int price = ((Number) product.get("price")).intValue();
        float feeRate = ((Number) product.get("fee_rate")).floatValue();

        int totalAmount = price * req.getPurchaseQuantity();
        int totalFee = Math.round(totalAmount * feeRate);

        preferenceDao.updatePreference(req.getSn(), req.getProductNo(),
            req.getPurchaseQuantity(), req.getAccount(), totalFee, totalAmount);
    }

    @Transactional
    public void deletePreference(int sn) {
        preferenceDao.deletePreference(sn);
    }

    @Transactional
    public void addProduct(String name, int price, float feeRate) {
        productDao.addProduct(name, price, feeRate);
    }

    @Transactional
    public void updateProduct(int no, String name, int price, float feeRate) {
        productDao.updateProduct(no, name, price, feeRate);
    }

    @Transactional
    public void deleteProduct(int no) {
        productDao.deleteProduct(no);
    }
}
