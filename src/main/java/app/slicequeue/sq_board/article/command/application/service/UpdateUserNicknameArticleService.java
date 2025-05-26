package app.slicequeue.sq_board.article.command.application.service;

import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.common.dto.UpdateUserNicknameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserNicknameArticleService {

    private final ArticleRepository articleRepository;

    public void update(UpdateUserNicknameCommand command) {
        articleRepository.updateUserNicknameByUserId(command.getNickname(), command.getUserId());
    }
}
