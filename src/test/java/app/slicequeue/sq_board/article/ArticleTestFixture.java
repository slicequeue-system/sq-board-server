package app.slicequeue.sq_board.article;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.board.command.domain.BoardId;

import java.util.ArrayList;
import java.util.List;

public class ArticleTestFixture {

    public static final BoardId BOARD_ID1 = BoardId.from(1L);
    public static final BoardId BOARD_ID2 = BoardId.from(2L);

    public static final long WRITER_ID1 = 100L;
    public static final long WRITER_ID2 = 200L;

    public static List<Article> createExamples(int count, BoardId boardId, long writerId) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            articles.add(Article.create(CreateArticleCommand.builder()
                            .boardId(boardId)
                            .title("게시글 %s".formatted(i))
                            .content("게시글 %s - 컨텐츠".formatted(i))
                            .tags(List.of("java", "spring"))
                            .writerId(writerId)
                            .writerName("작성자")
                    .build()));
        }
        return articles;
    }

}
