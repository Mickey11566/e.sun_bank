DELIMITER //

-- 1. 金融商品 (Product) 維護
DROP PROCEDURE IF EXISTS sp_get_all_products //
CREATE PROCEDURE sp_get_all_products()
BEGIN
    SELECT no, product_name, price, fee_rate FROM product;
END //

DROP PROCEDURE IF EXISTS sp_get_product //
CREATE PROCEDURE sp_get_product(
    IN p_no INT
)
BEGIN
    SELECT no, product_name, price, fee_rate FROM product WHERE no = p_no;
END //

DROP PROCEDURE IF EXISTS sp_add_product //
CREATE PROCEDURE sp_add_product(
    IN p_product_name VARCHAR(255),
    IN p_price INT,
    IN p_fee_rate FLOAT
)
BEGIN
    INSERT INTO product (product_name, price, fee_rate)
    VALUES (p_product_name, p_price, p_fee_rate);
END //

DROP PROCEDURE IF EXISTS sp_update_product //
CREATE PROCEDURE sp_update_product(
    IN p_no INT,
    IN p_product_name VARCHAR(255),
    IN p_price INT,
    IN p_fee_rate FLOAT
)
BEGIN
    UPDATE product
    SET product_name = p_product_name,
        price = p_price,
        fee_rate = p_fee_rate
    WHERE no = p_no;
END //

DROP PROCEDURE IF EXISTS sp_delete_product //
CREATE PROCEDURE sp_delete_product(
    IN p_no INT
)
BEGIN
    DELETE FROM product WHERE no = p_no;
END //

-- 2. 查詢專屬喜好清單
DROP PROCEDURE IF EXISTS sp_get_all_preferences //
CREATE PROCEDURE sp_get_all_preferences(
    IN p_account VARCHAR(50)
)
BEGIN
    SELECT 
        l.sn,
        l.product_no,
        p.product_name,
        p.price,
        p.fee_rate,
        l.purchase_quantity,
        l.account,
        -- 使用 JOIN 的產品最新價格即時計算
        (p.price * l.purchase_quantity * p.fee_rate) as total_fee,
        (p.price * l.purchase_quantity) as total_amount,
        u.email
    FROM like_list l
    LEFT JOIN product p ON l.product_no = p.no
    LEFT JOIN user u ON l.account = u.account
    WHERE l.account = p_account;
END //

-- 3. 新增喜好記錄
DROP PROCEDURE IF EXISTS sp_add_preference //
CREATE PROCEDURE sp_add_preference(
    IN p_product_no INT,
    IN p_purchase_quantity INT,
    IN p_account VARCHAR(255),
    IN p_total_fee INT,
    IN p_total_amount INT
)
BEGIN
    INSERT INTO like_list (product_no, purchase_quantity, account, total_fee, total_amount)
    VALUES (p_product_no, p_purchase_quantity, p_account, p_total_fee, p_total_amount);
END //

-- 4. 更新喜好記錄
DROP PROCEDURE IF EXISTS sp_update_preference //
CREATE PROCEDURE sp_update_preference(
    IN p_sn INT,
    IN p_product_no INT,
    IN p_purchase_quantity INT,
    IN p_account VARCHAR(255),
    IN p_total_fee INT,
    IN p_total_amount INT
)
BEGIN
    UPDATE like_list
    SET 
        product_no = p_product_no,
        purchase_quantity = p_purchase_quantity,
        account = p_account,
        total_fee = p_total_fee,
        total_amount = p_total_amount
    WHERE sn = p_sn;
END //

-- 5. 刪除喜好記錄
DROP PROCEDURE IF EXISTS sp_delete_preference //
CREATE PROCEDURE sp_delete_preference(
    IN p_sn INT
)
BEGIN
    DELETE FROM like_list WHERE sn = p_sn;
END //

-- 6. 新增用戶
DROP PROCEDURE IF EXISTS sp_add_user //
CREATE PROCEDURE sp_add_user(
    IN p_user_id VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_user_name VARCHAR(255),
    IN p_account VARCHAR(255)
)
BEGIN
    -- 檢查 Email 是否已存在
    IF EXISTS (SELECT 1 FROM user WHERE email = p_email) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EMAIL_ALREADY_EXISTS';
    ELSE
        INSERT INTO user(user_id, email, password, user_name, account)
        VALUES (p_user_id, p_email, p_password, p_user_name, p_account);
    END IF;
END //

-- 7. 透過 Email 查詢用戶
DROP PROCEDURE IF EXISTS sp_get_user_by_email //
CREATE PROCEDURE sp_get_user_by_email(
    IN p_email VARCHAR(255)
)
BEGIN
    SELECT * FROM user WHERE email = p_email;
END //

DELIMITER ;
