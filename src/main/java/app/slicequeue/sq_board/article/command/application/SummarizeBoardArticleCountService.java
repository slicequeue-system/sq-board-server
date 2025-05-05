package app.slicequeue.sq_board.article.command.application;

import app.slicequeue.sq_board.article.command.domain.BoardArticleCount;
import app.slicequeue.sq_board.article.command.domain.BoardArticleCountRepository;
import app.slicequeue.sq_board.article.command.domain.dto.BoardArticleCountCommand;
import app.slicequeue.sq_board.common.callback.LockedCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeBoardArticleCountService {

    private final BoardArticleCountRepository boardArticleCountRepository;

    @Transactional
    public void increaseWithLock(BoardArticleCountCommand command, LockedCallback callback) {
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findLockedByBoardId(command.boardId())
                .orElse(BoardArticleCount.createCountZero(command));
        boardArticleCount.increaseCount(command.lastCreatedAt());
        boardArticleCountRepository.save(boardArticleCount);
        callback.execute();
    }

    @Transactional
    public void decreaseWithLock(BoardArticleCountCommand command, LockedCallback callback) {
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findLockedByBoardId(command.boardId())
                .orElse(BoardArticleCount.createCountZero(command));
        boardArticleCount.decreaseCount();
        boardArticleCountRepository.save(boardArticleCount);
        callback.execute();
    }
}
