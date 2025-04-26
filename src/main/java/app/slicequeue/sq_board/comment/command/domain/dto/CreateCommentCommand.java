package app.slicequeue.sq_board.comment.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.presentation.dto.CreateCommentRequest;

public record CreateCommentCommand(String content,
                                   ArticleId articleId,
                                   Long writerId,
                                   String writerNickname,
                                   CommentPath parentPath) {
    public static CreateCommentCommand from(CreateCommentRequest request) {
        return new CreateCommentCommand(
                request.getContent(),
                ArticleId.from(request.getArticleId()),
                request.getWriterId(),
                request.getWriterNickname(),
                request.getParentPath() != null
                    ? CommentPath.create(request.getParentPath())
                    : null);
    }
}
