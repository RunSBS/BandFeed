ALTER TABLE `posts`
    ADD COLUMN `band_name` varchar(100) NULL AFTER `band_id`,
    ADD COLUMN `band_image_url` varchar(500) NULL AFTER `band_name`;
