package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteArticleReactionService {

    private final ArticleReactionRepository articleReactionRepository;

    @Transactional
    void delete(ArticleReaction articleReaction) {
        articleReactionRepository.delete(articleReaction);
    }
}
