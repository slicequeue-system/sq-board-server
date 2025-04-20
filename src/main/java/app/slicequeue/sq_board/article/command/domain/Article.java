package app.slicequeue.sq_board.article.command.domain;

import app.slicequeue.common.base.time_entity.BaseTimeSoftDeleteEntity;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.article.command.domain.dto.UpdateArticleCommand;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.common.util.StringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Table(name = "article", indexes = {
        @Index(name = "idx_board_id_article_id", columnList = "board_id,article_id desc")})
@Entity
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeSoftDeleteEntity {

    @EmbeddedId
    private ArticleId articleId;

    @NotNull(message = "boardId must not be null")
    private BoardId boardId; // shard key

    @Size(min = 1, max = 200)
    @NotNull(message = "title must not be null")
    private String title;

    @NotNull(message = "writerId must not be null")
    private Long writerId;

    @NotNull(message = "writerName must not be null")
    private String writerName;

    @Column(columnDefinition = "TEXT NOT NULL") // 최대 길이 65,535자 (단, 영문 기준! 한글 및 이모지 포함시 더 줄어듦)
    @Size(min = 1, max = 20000) // 따라서 길이 제한 20000 으로
    @NotNull(message = "content must not be null")
    private String content;

    @Convert(converter = StringListConverter.class)
    private List<String> tags; // TODO 추후 태그 최적화를 위해 M:N 맵핑 처리 진행할 것

    public static Article create(CreateArticleCommand command) {
        Article article = new Article();
        article.boardId = command.boardId();
        article.title = command.title();
        article.writerId = command.writerId();
        article.writerName = command.writerName();
        article.content = command.content();
        article.tags = command.tags();

        article.articleId = ArticleId.generateId();
        return article;
    }

    public void update(UpdateArticleCommand command) {
        this.title = command.title();
        this.content = command.content();
        this.tags = command.tags();
    }

    public void delete() {
        nowDeletedAt();
    }
}
