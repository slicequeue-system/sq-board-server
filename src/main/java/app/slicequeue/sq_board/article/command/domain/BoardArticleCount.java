package app.slicequeue.sq_board.article.command.domain;

import app.slicequeue.sq_board.article.command.domain.dto.BoardArticleCountCommand;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Table(name = "board_article_count")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardArticleCount {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "board_id"))
    private BoardId boardId;
    private long articleCount;
    private Instant lastCreatedAt;

    public static BoardArticleCount createCountZero(BoardArticleCountCommand command) {
        BoardArticleCount boardArticleCount = new BoardArticleCount();
        boardArticleCount.boardId = command.boardId();
        boardArticleCount.articleCount = 0;
        boardArticleCount.lastCreatedAt = Instant.now();
        return boardArticleCount;
    }

    public void increaseCount(Instant articleLastCreatedAt) {
        articleCount = articleCount + 1;
        lastCreatedAt = articleLastCreatedAt;
    }

    public void decreaseCount() {
        if (articleCount == 0) return;
        articleCount = articleCount - 1;
    }
}
