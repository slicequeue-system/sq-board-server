package app.slicequeue.sq_board.article.command.domain;

import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ArticleId {

    static Snowflake snowflake = new Snowflake();

    @NotNull
    @Comment("게시글식별값")
    @Column(name = "article_id")
    private Long id;

    public ArticleId(Long id) {
        this.id = id;
    }

    public static ArticleId generateId() {
        ArticleId articleId = new ArticleId();
        articleId.id = snowflake.nextId();
        return articleId;
    }

    public static ArticleId from(Long idValue) {
        Assert.notNull(idValue, "id must not be null");
        if (idValue <= 0)
            throw new IllegalArgumentException("id must be positive value");
        return new ArticleId(idValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleId articleId)) return false;
        return Objects.equals(getId(), articleId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
