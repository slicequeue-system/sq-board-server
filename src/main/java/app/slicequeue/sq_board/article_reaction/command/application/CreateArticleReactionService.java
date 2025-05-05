package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.CreateArticleReactionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateArticleReactionService {

    private final ArticleReactionRepository articleReactionRepository;

    public ArticleReactionId create(CreateArticleReactionCommand command) {
        ArticleReaction reaction = ArticleReaction.create(command);
        return articleReactionRepository.save(reaction).getArticleReactionId();
    }
}
