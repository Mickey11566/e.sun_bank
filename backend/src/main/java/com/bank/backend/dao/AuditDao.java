package com.bank.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class AuditDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 記錄稽核日誌
     * @param user 使用者帳號或ID
     * @param action 操作類型 (CREATE, UPDATE, DELETE)
     * @param tableName 被操作的資料表
     * @param recordId 被操作的紀錄 ID (可為 null)
     */
    public void log(String user, String action, String tableName, Integer recordId) {
        String sql = "INSERT INTO audit_log (user_id, action, table_name, record_id, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user, action, tableName, recordId, LocalDateTime.now());
    }
}
