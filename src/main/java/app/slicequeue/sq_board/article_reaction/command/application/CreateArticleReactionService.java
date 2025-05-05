package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateArticleReactionService {

    private final ArticleReactionRepository articleReactionRepository;

    @Transactional
    public ArticleReaction create(ArticleReactionCommand command) {
        ArticleReaction reaction = ArticleReaction.create(command);
        return articleReactionRepository.save(reaction);
    }
}
