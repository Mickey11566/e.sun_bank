package com.bank.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 金融喜好資料存取層 (Data Access Object)
 * 所有資料操作均透過 JdbcTemplate 呼叫資料庫預存程序 (Stored Procedure)
 */
@Repository
public class PreferenceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查詢特定帳號的所有喜好紀錄
     * @param account 使用者專屬帳號
     * @return 包含動態計算金額的資料清單
     */
    public List<Map<String, Object>> getAllPreferences(String account) {
        return jdbcTemplate.queryForList("CALL sp_get_all_preferences(?)", account);
    }

    /** 
	 * 新增喜好設定
	 */
    public void addPreference(int productNo, int quantity, String account, int totalFee, int totalAmount) {
        jdbcTemplate.update("CALL sp_add_preference(?, ?, ?, ?, ?)",
            productNo, quantity, account, totalFee, totalAmount);
    }

    /** 
	 * 更新喜好設定
	 */
    public void updatePreference(int sn, int productNo, int quantity, String account, int totalFee, int totalAmount) {
        jdbcTemplate.update("CALL sp_update_preference(?, ?, ?, ?, ?, ?)",
            sn, productNo, quantity, account, totalFee, totalAmount);
    }

    /** 
	 * 刪除喜好設定
	 */
    public void deletePreference(int sn) {
        jdbcTemplate.update("CALL sp_delete_preference(?)", sn);
    }
}
