package app.slicequeue.sq_board.article.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ArticleId extends BaseSnowflakeId<ArticleId> {

    static Snowflake snowflake = new Snowflake();

    public ArticleId(Long id) {
        super(id);
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }

    public static ArticleId generateId() {
        return new ArticleId().generate();
    }

    public static ArticleId from(Long idValue) {
        return from(idValue, ArticleId.class);
    }
}
