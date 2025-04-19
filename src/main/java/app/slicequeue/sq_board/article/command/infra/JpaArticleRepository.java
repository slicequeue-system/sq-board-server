package app.slicequeue.sq_board.article.command.infra;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleRepository extends ArticleRepository, JpaRepository<Article, ArticleId> {

    Optional<Article> findByArticleId(ArticleId articleId);
}
