package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCount;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.IncreaseArticleReactionCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeArticleReactionCountService {

    private final ArticleReactionCountRepository articleReactionCountRepository;

    @FunctionalInterface
    public interface LockedReactionCountCallback {
        void execute(ArticleReactionCount count);
    }

    @Transactional
    public void increaseWithLock(IncreaseArticleReactionCountCommand command, LockedReactionCountCallback callback) {
        ArticleReactionCount articleReactionCount = articleReactionCountRepository
                .findLockedByArticleReactionCountId(ArticleReactionCountId.from(command))
                .orElse(ArticleReactionCount.createCountZero(command));
        articleReactionCount.increaseCount();
        articleReactionCountRepository.save(articleReactionCount);
        callback.execute(articleReactionCount);
    }
}



