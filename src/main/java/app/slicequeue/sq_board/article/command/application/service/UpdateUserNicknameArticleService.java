package app.slicequeue.sq_board.article.command.application.service;

import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.application.dto.UpdateUserNicknameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserNicknameArticleService {

    private final ArticleRepository articleRepository;

    public int update(UpdateUserNicknameCommand command) {
        return articleRepository.updateUserNicknameByUserId(command.getNickname(), command.getUserId());
    }
}
