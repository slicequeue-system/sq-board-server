package app.slicequeue.sq_board.article.query.presentation.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleDetail {
    private String articleId;
    private String title;
    private String writerName;
    private String content;
    private List<String> tags;

    public ArticleDetail(ArticleId articleId,
                         String title,
                         String content,
                         String writerName,
                         List<String> tags) {
        this.articleId = articleId.toString();
        this.title = title;
        this.writerName = writerName;
        this.content = content;
        this.tags = tags;
    }
}
