package app.slicequeue.sq_board.article.command.infra;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JpaArticleRepository extends ArticleRepository, JpaRepository<Article, ArticleId> {

    Optional<Article> findByArticleIdAndDeletedAtIsNull(ArticleId articleId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE article SET writer_name = :nickname WHERE writer_id = :userId;
            """, nativeQuery = true)
    int updateUserNicknameByUserId(@Param("nickname") String nickname, @Param("userId") long userId);
}
