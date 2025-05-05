package app.slicequeue.sq_board.article_reaction.command.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ArticleReactionRepository {

    ArticleReaction save(ArticleReaction articleReaction);
}
