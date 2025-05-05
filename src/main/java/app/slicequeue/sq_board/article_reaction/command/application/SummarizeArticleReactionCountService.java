package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCount;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.IncreaseArticleReactionCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummarizeArticleReactionCountService {

    private final ArticleReactionCountRepository articleReactionCountRepository;

    @Transactional
    public void increase(IncreaseArticleReactionCountCommand command) {
        ArticleReactionCount articleReactionCount = articleReactionCountRepository
                .findLockedByArticleIdAndEmoji(command.articleId(), command.emoji())
                .orElse(ArticleReactionCount.createCountZero(command));

        // 여기 사이에 CreateArticleReactionService create 메서드 수행하는 부분 락 잡히는 것 고려하면 이부분에 해야할 필요는 없을까?

        articleReactionCount.increaseCount();
        articleReactionCountRepository.save(articleReactionCount);
    }
}



