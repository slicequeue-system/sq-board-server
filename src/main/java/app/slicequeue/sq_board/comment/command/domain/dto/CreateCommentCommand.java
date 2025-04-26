package app.slicequeue.sq_board.comment.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;

public record CreateCommentCommand(CommentId commentId,
                                   String content,
                                   ArticleId articleId,
                                   Long writerId,
                                   String writerNickname,
                                   CommentPath path) { }
