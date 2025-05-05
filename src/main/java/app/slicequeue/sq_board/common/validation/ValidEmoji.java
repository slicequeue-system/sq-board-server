package app.slicequeue.sq_board.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmojiValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmoji {
    String message() default "유효하지 않은 이모지입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int max() default 1; // 허용할 이모지 최대 개수
}
