-- =====================================================================
-- xcdld manager SSO 绑定字段（可重复执行）
-- 为 tb_account 增加 manager_user_id 字段（绑定 xechat-manager 的 tb_sys_user.id）
-- 首次 SSO 登录时写入，后续登录按此字段复用账号与玩家数据
-- =====================================================================

DROP PROCEDURE IF EXISTS add_manager_user_id_if_not_exists;
DELIMITER $$
CREATE PROCEDURE add_manager_user_id_if_not_exists()
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.COLUMNS
                   WHERE TABLE_SCHEMA = DATABASE()
                     AND TABLE_NAME = 'tb_account'
                     AND COLUMN_NAME = 'manager_user_id') THEN
        ALTER TABLE tb_account
            ADD COLUMN manager_user_id BIGINT NULL COMMENT 'manager平台用户ID(SSO绑定)' AFTER platform;
        ALTER TABLE tb_account
            ADD UNIQUE INDEX uk_manager_user_id (manager_user_id);
    END IF;
END$$
DELIMITER ;
CALL add_manager_user_id_if_not_exists();
DROP PROCEDURE IF EXISTS add_manager_user_id_if_not_exists;
