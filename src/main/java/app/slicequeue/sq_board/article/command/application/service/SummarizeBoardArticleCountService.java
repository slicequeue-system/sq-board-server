package app.slicequeue.sq_board.article.command.application.service;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.BoardArticleCount;
import app.slicequeue.sq_board.article.command.domain.BoardArticleCountRepository;
import app.slicequeue.sq_board.article.command.domain.dto.BoardArticleCountCommand;
import app.slicequeue.sq_board.common.callback.LockedReturnCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeBoardArticleCountService {

    private final BoardArticleCountRepository boardArticleCountRepository;

    @Transactional
    public void increaseWithLock(LockedReturnCallback<Article> callback) {
        Article createdArticle = callback.execute();
        BoardArticleCountCommand command = BoardArticleCountCommand.from(createdArticle);
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findLockedByBoardId(command.boardId())
                .orElse(BoardArticleCount.createCountZero(command));
        boardArticleCount.increaseCount(command.lastCreatedAt());
        boardArticleCountRepository.save(boardArticleCount);
    }

    @Transactional
    public void decreaseWithLock(LockedReturnCallback<Article> callback) {
        Article deletedArticle = callback.execute();
        BoardArticleCountCommand command = BoardArticleCountCommand.from(deletedArticle);
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findLockedByBoardId(command.boardId())
                .orElse(BoardArticleCount.createCountZero(command));
        boardArticleCount.decreaseCount();
        boardArticleCountRepository.save(boardArticleCount);
    }
}
