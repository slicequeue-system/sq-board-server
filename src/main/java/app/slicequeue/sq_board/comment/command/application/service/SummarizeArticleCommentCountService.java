package app.slicequeue.sq_board.comment.command.application.service;

import app.slicequeue.sq_board.comment.command.domain.ArticleCommentCount;
import app.slicequeue.sq_board.comment.command.domain.ArticleCommentCountRepository;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.dto.ArticleCommentCountCommand;
import app.slicequeue.sq_board.common.callback.LockedReturnCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeArticleCommentCountService {

    private final ArticleCommentCountRepository articleCommentCountRepository;

    @Transactional
    public void increaseWithLock(LockedReturnCallback<Comment> callback) {
        ArticleCommentCountCommand command = ArticleCommentCountCommand.from(callback.execute());
        ArticleCommentCount articleCommentCount =
                articleCommentCountRepository
                        .findLockedByArticleId(command.articleId())
                        .orElse(ArticleCommentCount.createCountZero(command));
        articleCommentCount.increaseCount(command.lastCreatedAt());
        articleCommentCountRepository.save(articleCommentCount);
    }

    @Transactional
    public void decreaseWithLock(LockedReturnCallback<Comment> callback) {
        Comment comment = callback.execute();
        ArticleCommentCountCommand command = ArticleCommentCountCommand.from(comment);
        ArticleCommentCount articleCommentCount =
                articleCommentCountRepository
                        .findLockedByArticleId(comment.getArticleId())
                        .orElse(ArticleCommentCount.createCountZero(command));
        articleCommentCount.decreaseCount();
        articleCommentCountRepository.save(articleCommentCount);
    }
}
