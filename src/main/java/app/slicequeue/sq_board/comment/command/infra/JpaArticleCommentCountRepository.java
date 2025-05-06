package app.slicequeue.sq_board.comment.command.infra;

import app.slicequeue.sq_board.comment.command.domain.ArticleCommentCountRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleCommentCountRepository extends ArticleCommentCountRepository {

}
