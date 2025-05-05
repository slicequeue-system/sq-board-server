package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCount;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import app.slicequeue.sq_board.common.callback.LockedCallback;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeArticleReactionCountService {

    private final ArticleReactionCountRepository articleReactionCountRepository;

    @Transactional
    public void increaseWithLock(ArticleReactionCountCommand command, LockedCallback callback) {
        ArticleReactionCount articleReactionCount = articleReactionCountRepository
                .findLockedByArticleReactionCountId(ArticleReactionCountId.from(command))
                .orElse(ArticleReactionCount.createCountZero(command));
        articleReactionCount.increaseCount();
        articleReactionCountRepository.save(articleReactionCount);
        callback.execute();
    }

    @Transactional
    public void decreaseWithLock(ArticleReactionCountCommand command, LockedCallback callback) {
        ArticleReactionCount articleReactionCount = articleReactionCountRepository
                .findLockedByArticleReactionCountId(ArticleReactionCountId.from(command))
                .orElse(ArticleReactionCount.createCountZero(command));
        articleReactionCount.decreaseCount();
        articleReactionCountRepository.save(articleReactionCount);
        callback.execute();
    }
}



