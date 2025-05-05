package app.slicequeue.sq_board.comment_reaction.command.application.service;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReaction;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommentReactionService {

    private final CommentReactionRepository commentReactionRepository;

    @Transactional
    public void delete(CommentReaction commentReaction) {
        commentReactionRepository.delete(commentReaction);
    }
}
