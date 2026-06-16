ALTER TABLE `band_members`
    ADD COLUMN `status` ENUM('PENDING', 'ACTIVE') NOT NULL DEFAULT 'ACTIVE' AFTER `role`;

-- 기존 멤버(밴드장)는 모두 ACTIVE
UPDATE `band_members` SET `status` = 'ACTIVE';
