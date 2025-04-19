package app.slicequeue.sq_board.article.command.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository {

    Article save(Article board);
}
