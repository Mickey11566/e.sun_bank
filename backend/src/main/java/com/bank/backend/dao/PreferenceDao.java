package com.bank.backend.dao;

import com.bank.backend.dto.PreferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<PreferenceDto> getAllPreferences(String account) {
        return jdbcTemplate.query("CALL sp_get_all_preferences(?)", 
            new BeanPropertyRowMapper<>(PreferenceDto.class), account);
    }

    /** 
	 * 新增喜好設定
     * @return 新產生的流水號 (sn)
	 */
    public int addPreference(int productNo, int quantity, String account, int totalFee, int totalAmount) {
        jdbcTemplate.update("CALL sp_add_preference(?, ?, ?, ?, ?)",
            productNo, quantity, account, totalFee, totalAmount);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
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
