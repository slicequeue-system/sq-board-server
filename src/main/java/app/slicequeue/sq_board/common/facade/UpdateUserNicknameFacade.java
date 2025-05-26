package app.slicequeue.sq_board.common.facade;

import app.slicequeue.sq_board.article.command.application.service.UpdateUserNicknameArticleService;
import app.slicequeue.sq_board.comment.command.application.service.UpdateUserNicknameCommentService;
import app.slicequeue.sq_board.common.dto.UpdateUserNicknameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserNicknameFacade {

    private final UpdateUserNicknameArticleService updateUserNicknameArticleService;
    private final UpdateUserNicknameCommentService updateUserNicknameCommentService;

    public void update(UpdateUserNicknameCommand command) {
        updateUserNicknameArticleService.update(command);
        updateUserNicknameCommentService.update(command);
    }
}
