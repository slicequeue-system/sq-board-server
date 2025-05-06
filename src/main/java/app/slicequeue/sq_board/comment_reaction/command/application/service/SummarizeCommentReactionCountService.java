package app.slicequeue.sq_board.comment_reaction.command.application.service;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCount;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountId;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCount;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountId;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountRepository;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCountCommand;
import app.slicequeue.sq_board.common.callback.LockedCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeCommentReactionCountService {

    private final CommentReactionCountRepository commentReactionCountRepository;

    @Transactional
    public void increaseWithLock(CommentReactionCountCommand command, LockedCallback callback) {
        CommentReactionCount reactionCount = getCommentReactionCountOrDefaultCountZero(command);
        reactionCount.increaseCount();
        commentReactionCountRepository.save(reactionCount);
        callback.execute();
    }

    @Transactional
    public void decreaseWithLock(CommentReactionCountCommand command, LockedCallback callback) {
        CommentReactionCount reactionCount = getCommentReactionCountOrDefaultCountZero(command);
        reactionCount.decreaseCount();
        if (reactionCount.needRemove())
            commentReactionCountRepository.delete(reactionCount);
        else
            commentReactionCountRepository.save(reactionCount);
        callback.execute();
    }

    private CommentReactionCount getCommentReactionCountOrDefaultCountZero(CommentReactionCountCommand command) {
        return commentReactionCountRepository
                .findLockedByCommentReactionCountId(CommentReactionCountId.from(command))
                .orElse(CommentReactionCount.createCountZero(command));
    }
}



