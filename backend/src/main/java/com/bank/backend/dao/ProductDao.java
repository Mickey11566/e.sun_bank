package com.bank.backend.dao;

import com.bank.backend.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** 
	 * 取得所有產品
	 */
    public List<ProductDto> getAllProducts() {
        return jdbcTemplate.query("CALL sp_get_all_products()", new BeanPropertyRowMapper<>(ProductDto.class));
    }

    /** 
	 * 取得單一產品
	 */
    public ProductDto getProduct(int no) {
        return jdbcTemplate.queryForObject("CALL sp_get_product(?)", new BeanPropertyRowMapper<>(ProductDto.class), no);
    }

    /** 
	 * 新增產品
     * @return 新產生的商品編號 (no)
	 */
    public int addProduct(String name, int price, float feeRate) {
        jdbcTemplate.update("CALL sp_add_product(?, ?, ?)", name, price, feeRate);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
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
