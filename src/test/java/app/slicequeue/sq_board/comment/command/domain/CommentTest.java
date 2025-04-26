package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentTest {

    @Autowired
    TestEntityManager entityManager;

    @Test
    void 코멘트엔티티_삽입한다() {
        // given
        Comment comment = Comment.create(new CreateCommentCommand(
                "내용",
                ArticleId.generateId(),
                123L,
                "작성자 닉네임",
                CommentPath.create("")
        ));

        // when
        Comment result = entityManager.persistAndFlush(comment);

        // then
        assertThat(result).isNotNull();
    }

}
