package app.slicequeue.sq_board.common.base;

import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;


@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractSnowflakeId<T extends AbstractSnowflakeId<T>> implements Serializable {

    @NotNull
    @Column(name = "id")
    protected Long id;

    protected AbstractSnowflakeId(Long id) {
        this.id = id;
    }

    protected abstract Snowflake getSnowflake();

    public T generate() {
        try {
            T instance = (T) this.getClass().getDeclaredConstructor().newInstance();
            instance.id = getSnowflake().nextId();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("ID 생성 실패", e);
        }
    }



    public static <T extends AbstractSnowflakeId<T>> T from(Long id, Class<T> clazz) {
        Assert.notNull(id, "id must not be null");
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        try {
            return clazz.getConstructor(Long.class).newInstance(id);
        } catch (Exception e) {
            throw new RuntimeException("ID from(Long) 생성 실패", e);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

