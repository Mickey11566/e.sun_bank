package com.bank.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** 
	 * 取得所有產品
	 */
    public List<Map<String, Object>> getAllProducts() {
        return jdbcTemplate.queryForList("CALL sp_get_all_products()");
    }

    /** 
	 * 取得單一產品
	 */
    public Map<String, Object> getProduct(int no) {
        return jdbcTemplate.queryForMap("CALL sp_get_product(?)", no);
    }

    /** 
	 * 新增產品
	 */
    public void addProduct(String name, int price, float feeRate) {
        jdbcTemplate.update("CALL sp_add_product(?, ?, ?)", name, price, feeRate);
    }

    /** 
	 * 更新產品
	 */
    public void updateProduct(int no, String name, int price, float feeRate) {
        jdbcTemplate.update("CALL sp_update_product(?, ?, ?, ?)", no, name, price, feeRate);
    }

    /** 
	 * 刪除產品
	 */
    public void deleteProduct(int no) {
        jdbcTemplate.update("CALL sp_delete_product(?)", no);
    }
}
