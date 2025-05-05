package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ArticleReactionId extends BaseSnowflakeId<ArticleReactionId> {

    private static Snowflake snowflake = new Snowflake();

    public ArticleReactionId(Long id) {
        super(id);
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }

    public static ArticleReactionId generateId() {
        return new ArticleReactionId().generate();
    }

    public static ArticleReactionId from(Long idValue) {
        return from(idValue, ArticleReactionId.class);
    }
}
