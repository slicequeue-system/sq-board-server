CREATE SCHEMA `sq-board` DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_general_ci;

CREATE TABLE board (
    board_id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    project_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    description VARCHAR(512) NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    KEY `idx_project_id_board_id` (`project_id` , `board_id` DESC)
);
