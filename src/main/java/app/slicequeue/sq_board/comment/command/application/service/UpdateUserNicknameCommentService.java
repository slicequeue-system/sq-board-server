package app.slicequeue.sq_board.comment.command.application.service;

import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import app.slicequeue.sq_board.common.dto.UpdateUserNicknameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserNicknameCommentService {

    private final CommentRepository commentRepository;

    public void update(UpdateUserNicknameCommand command) {
        commentRepository.updateUserNicknameByUserId(command.getNickname(), command.getUserId());
    }
}
