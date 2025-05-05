package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.CreateArticleReactionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateArticleReactionService {

    private final ArticleReactionRepository articleReactionRepository;

    @Transactional
    public ArticleReaction create(CreateArticleReactionCommand command) {
        ArticleReaction reaction = ArticleReaction.create(command);
        return articleReactionRepository.save(reaction);
    }
}
