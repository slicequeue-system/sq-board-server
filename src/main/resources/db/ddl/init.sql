CREATE SCHEMA `sq-board` DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_general_ci;
use `sq-board`;

CREATE TABLE board (
   board_id bigint NOT NULL,
   name varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
   project_id bigint NOT NULL,
   admin_id bigint NOT NULL,
   description varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL,
   deleted_at datetime NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   PRIMARY KEY (board_id),
   KEY idx_project_id_board_id (project_id,board_id DESC)
);
