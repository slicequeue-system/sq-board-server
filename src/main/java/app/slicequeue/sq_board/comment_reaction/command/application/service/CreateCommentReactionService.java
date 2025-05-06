package app.slicequeue.sq_board.comment_reaction.command.application.service;

import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReaction;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionRepository;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCommentReactionService {

    private final CommentReactionRepository commentReactionRepository;

    @Transactional
    public CommentReaction create(CommentReactionCommand command) {
        CommentReaction reaction = CommentReaction.create(command);
        return commentReactionRepository.save(reaction);
    }
}
