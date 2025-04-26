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

CREATE TABLE article (
   article_id bigint NOT NULL,
   board_id bigint NOT NULL,
   title varchar(512) COLLATE utf8mb4_general_ci NOT NULL,
   writer_id bigint NOT NULL,
   writer_name varchar(512) COLLATE utf8mb4_general_ci NOT NULL,
   tags varchar(512) COLLATE utf8mb4_general_ci NOT NULL,
   content TEXT COLLATE utf8mb4_general_ci NOT NULL,
   deleted_at datetime NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   PRIMARY KEY (article_id),
   KEY idx_board_id_article_id (board_id, article_id DESC)
);

create table comment(
	comment_id bigint not null primary key,
    content varchar(3000) not null,
    article_id bigint not null,
    parent_comment_id bigint NULL,
    writer_id bigint not null,
    writer_nickname varchar(100) not null,
    path varchar(25) character set utf8mb4 collate utf8mb4_bin not null,
    deleted boolean not null default false,
    created_at datetime not null,
    updated_at datetime NOT NULL
);

create unique index idx_article_id_path on comment(article_id asc, path asc);
create unique index idx_article_id_parent_comment_id_comment_id on comment(article_id, parent_comment_id, comment_id);
