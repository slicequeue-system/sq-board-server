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

    @SuppressWarnings("unchecked") // 타입 안정성은 우리가 보장하고 있어서 문제 없음
    public T generate() {
        try {
            var constructor = this.getClass().getDeclaredConstructor();
            constructor.setAccessible(true); // 여기 추가! (Java 8)
            T instance = (T) constructor.newInstance();
            instance.id = getSnowflake().nextId();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("ID 생성 실패", e);
        }
    }

    protected static <T extends AbstractSnowflakeId<T>> T from(Long id, Class<T> clazz) {
        Assert.notNull(id, "id must not be null");
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        try {
            return clazz.getConstructor(Long.class).newInstance(id);
        } catch (Exception e) {
            throw new RuntimeException("id from(Long) generate fail", e);
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

