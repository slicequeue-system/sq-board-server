package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.common.base.time_entity.BaseTimeSoftDeletedEntity;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comment", indexes = {
        @Index(name = "idx_article_id_path", columnList = "article_id,path"),
        @Index(name = "idx_article_id_parent_comment_id_comment_id", columnList = "article_id,parent_comment_id,comment_id"),
})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeSoftDeletedEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "comment_id"))
    private CommentId commentId;
    @Size(min = 1, max = 3000)
    @NotNull(message = "content must not be null")
    private String content;
    @NotNull(message = "articleId must not be null")
    private ArticleId articleId;
    @AttributeOverride(name = "id", column = @Column(name = "parent_comment_id"))
    private CommentId parentCommentId;
    @NotNull(message = "writerId must not be null")
    private Long writerId;
    @NotNull(message = "writerName must not be null")
    private String writerNickname;
    @NotNull(message = "path must not be null")
    private CommentPath path;

    public static Comment create(CreateCommentCommand command) {
        Comment comment = new Comment();
        comment.commentId = command.commentId();
        comment.content = command.content();
        comment.articleId = command.articleId();
        comment.writerId = command.writerId();
        comment.writerNickname = command.writerNickname();
        comment.path = command.path();
        return comment;
    }
}

