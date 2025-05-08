CREATE SCHEMA sq-board DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_general_ci;
use sq-board;

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

CREATE TABLE article_reaction (
  article_reaction_id BIGINT NOT NULL COMMENT '게시글 리액션 식별값',
  article_id BIGINT NOT NULL COMMENT '좋아요 대상 글 식별값',
  emoji VARCHAR(6) NOT NULL COMMENT '사용자 리액션 이모지',
  user_id BIGINT NOT NULL COMMENT '사용자 식별값',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  PRIMARY KEY (article_reaction_id),
  UNIQUE INDEX unq_article_id_emoji_user_id (article_id, emoji, user_id),
  INDEX fk_ar_article_id_a_article_id_idx (article_id),
  CONSTRAINT fk_ar_article_id_a_article_id
    FOREIGN KEY (article_id)
    REFERENCES article (article_id))
COMMENT = '글 리엑션';

CREATE TABLE article_reaction (
  article_reaction_id BIGINT NOT NULL COMMENT '게시글 리액션 식별값',
  article_id BIGINT NOT NULL COMMENT '좋아요 대상 글 식별값',
  emoji VARCHAR(6) NOT NULL COMMENT '사용자 리액션 이모지' character set utf8mb4 COLLATE utf8mb4_bin,
  user_id BIGINT NOT NULL COMMENT '사용자 식별값',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  PRIMARY KEY (article_reaction_id),
  UNIQUE INDEX unq_article_id_emoji_user_id (article_id, emoji, user_id),
  CONSTRAINT fk_ar_article_id_a_article_id
    FOREIGN KEY (article_id)
    REFERENCES article(article_id))
COMMENT = '글 리엑션';

CREATE TABLE article_reaction_count (
  article_id BIGINT NOT NULL COMMENT '게시글 식별값',
  emoji VARCHAR(6) NOT NULL COMMENT '사용자 리액션 이모지' character set utf8mb4 COLLATE utf8mb4_bin,
  count INT NOT NULL DEFAULT 0 COMMENT '사용자 리액션 이모지 숫자',
  PRIMARY KEY (article_id, emoji),
  CONSTRAINT fk_arc_article_id_a_article_id
    FOREIGN KEY (article_id)
    REFERENCES article(article_id)
)
COMMENT = '게시글 리액션 카운트 집계';

CREATE TABLE board_article_count (
  board_id BIGINT NOT NULL COMMENT '게시판 식별값',
  article_count BIGINT NOT NULL COMMENT '게시판 게시글 개수',
  last_created_at DATETIME NOT NULL COMMENT '마지막 등록 일시',
  PRIMARY KEY (board_id),
  CONSTRAINT fk_bac_board_id_b_board_id
    FOREIGN KEY (board_id)
    REFERENCES board (board_id))
COMMENT = '게시판의 게시글 카운트 집계';

CREATE TABLE comment_reaction (
  comment_reaction_id BIGINT NOT NULL COMMENT '댓글 리액션 식별값',
  comment_id BIGINT NOT NULL COMMENT '댓글 식별값',
  emoji VARCHAR(6) NOT NULL COMMENT '사용자 리액션 이모지' character set utf8mb4 COLLATE utf8mb4_bin,
  user_id BIGINT NOT NULL COMMENT '사용자 식별값',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  PRIMARY KEY (comment_reaction_id),
  CONSTRAINT fk_cr_comment_id_c_comment_id
    FOREIGN KEY (comment_id)
    REFERENCES comment (comment_id))
COMMENT = '댓글 리액션 - 사용자 이모지 반응 수집';

CREATE TABLE comment_reaction_count (
  comment_id BIGINT NOT NULL COMMENT '댓글 식별값',
  emoji VARCHAR(6) NOT NULL COMMENT '사용자 리액션 이모지' character set utf8mb4 COLLATE utf8mb4_bin,
  count INT NOT NULL DEFAULT 0 COMMENT '사용자 리액션 이모지 숫자',
  PRIMARY KEY (comment_id, emoji),
  CONSTRAINT fk_crc_comment_id_c_comment_id
    FOREIGN KEY (comment_id)
    REFERENCES comment (comment_id))
COMMENT = '댓글 리엑션 카운트 집계 - 사용자 이모지 반응 집계';

CREATE TABLE article_comment_count (
  article_id BIGINT NOT NULL,
  comment_count BIGINT NOT NULL COMMENT '게시판 게시글 개수',
  last_created_at DATETIME NOT NULL COMMENT '마지막 등록 일시',
  PRIMARY KEY (article_id),
  CONSTRAINT fk_bcc_article_id_a_article_id
    FOREIGN KEY (article_id)
    REFERENCES article (article_id))
COMMENT = '게시판의 게시글 카운트 집계';

CREATE TABLE article_view_count (
  article_id BIGINT NOT NULL COMMENT '게시글 식별값',
  view_count BIGINT NOT NULL COMMENT '게시글 조회수 카운트',
  PRIMARY KEY (article_id),
  CONSTRAINT fk_article_view_count_article1
    FOREIGN KEY (article_id)
    REFERENCES article (article_id))
COMMENT = '게시글 조회수 집계';
