package com.bank.backend.controller;

import com.bank.backend.dto.PreferenceDto;
import com.bank.backend.request.PreferenceReq;
import com.bank.backend.service.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 使用者偏好紀錄控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    /**
     * 取得特定帳號的所有偏好設定
     */
    @GetMapping
    public ResponseEntity<List<PreferenceDto>> getAllPreferences(@RequestParam("account") String account) {
        return ResponseEntity.ok(preferenceService.getAllPreferences(account));
    }

    /**
     * 新增個人偏好商品
     */
    @PostMapping
    public ResponseEntity<String> addPreference(@Valid @RequestBody PreferenceReq request) {
        preferenceService.addPreference(request);
        return ResponseEntity.ok("新增偏好商品成功");
    }

    /**
     * 更新個人偏好商品 (如調整數量)
     */
    @PutMapping
    public ResponseEntity<String> updatePreference(@Valid @RequestBody PreferenceReq request) {
        preferenceService.updatePreference(request);
        return ResponseEntity.ok("偏好商品更新成功");
    }

    /**
     * 刪除個人偏好商品
     */
    @DeleteMapping("/{sn}")
    public ResponseEntity<String> deletePreference(@PathVariable("sn") int sn) {
        preferenceService.deletePreference(sn);
        return ResponseEntity.ok("偏好商品刪除成功");
    }
}
