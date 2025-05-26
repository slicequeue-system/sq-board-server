package app.slicequeue.sq_board.article.command.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ArticleRepository {

    Article save(Article board);

    Optional<Article> findByArticleIdAndDeletedAtIsNull(ArticleId articleId);

    @Modifying
    @Transactional
    int updateUserNicknameByUserId(String nickname, long userId);
}
