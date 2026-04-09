package com.bank.backend.service;

import com.bank.backend.config.UserContext;
import com.bank.backend.dao.AuditDao;
import com.bank.backend.dao.PreferenceDao;
import com.bank.backend.dao.ProductDao;
import com.bank.backend.dto.PreferenceDto;
import com.bank.backend.dto.ProductDto;
import com.bank.backend.request.PreferenceReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceDao preferenceDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AuditDao auditDao;

    public List<PreferenceDto> getAllPreferences(String account) {
        return preferenceDao.getAllPreferences(account);
    }
    
    public List<ProductDto> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public void addPreference(PreferenceReq req) {
        ProductDto product = productDao.getProduct(req.getProductNo());
        if (product == null) {
            throw new IllegalArgumentException("找不到該商品");
        }

        int price = product.getPrice();
        float feeRate = product.getFeeRate();

        int totalAmount = price * req.getPurchaseQuantity();
        int totalFee = Math.round(totalAmount * feeRate);

        int newSn = preferenceDao.addPreference(req.getProductNo(), req.getPurchaseQuantity(),
            req.getAccount(), totalFee, totalAmount);
        
        auditDao.log(UserContext.getUser(), "CREATE", "like_list", newSn);
    }

    @Transactional
    public void updatePreference(PreferenceReq req) {
        ProductDto product = productDao.getProduct(req.getProductNo());
        if (product == null) {
            throw new IllegalArgumentException("找不到該商品");
        }

        int price = product.getPrice();
        float feeRate = product.getFeeRate();

        int totalAmount = price * req.getPurchaseQuantity();
        int totalFee = Math.round(totalAmount * feeRate);

        preferenceDao.updatePreference(req.getSn(), req.getProductNo(),
            req.getPurchaseQuantity(), req.getAccount(), totalFee, totalAmount);
        
        auditDao.log(UserContext.getUser(), "UPDATE", "like_list", req.getSn());
    }

    @Transactional
    public void deletePreference(int sn) {
        preferenceDao.deletePreference(sn);
        auditDao.log(UserContext.getUser(), "DELETE", "like_list", sn);
    }

    @Transactional
    public void addProduct(String name, int price, float feeRate) {
        int newNo = productDao.addProduct(name, price, feeRate);
        auditDao.log(UserContext.getUser(), "CREATE", "product", newNo);
    }

    @Transactional
    public void updateProduct(int no, String name, int price, float feeRate) {
        productDao.updateProduct(no, name, price, feeRate);
        auditDao.log(UserContext.getUser(), "UPDATE", "product", no);
    }

    @Transactional
    public void deleteProduct(int no) {
        productDao.deleteProduct(no);
        auditDao.log(UserContext.getUser(), "DELETE", "product", no);
    }
}
