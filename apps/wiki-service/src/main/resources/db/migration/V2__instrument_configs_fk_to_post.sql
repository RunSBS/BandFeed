ALTER TABLE `instrument_configs`
    DROP COLUMN `song_id`,
    DROP COLUMN `difficulty`,
    DROP COLUMN `notes`,
    ADD COLUMN `post_id` binary(16) NOT NULL AFTER `id`;
