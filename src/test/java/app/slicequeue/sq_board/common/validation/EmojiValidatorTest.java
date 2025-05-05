package app.slicequeue.sq_board.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EmojiValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void 이모지_1개_유효성_통과() {
        OnlyOneEmojiAllowedInput input = new OnlyOneEmojiAllowedInput("🔥");
        Set<ConstraintViolation<OnlyOneEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).isEmpty();
    }

    @Test
    void 이모지_2개_지정_정상() {
        TwoEmojiAllowedInput input = new TwoEmojiAllowedInput("🔥❤️");
        Set<ConstraintViolation<TwoEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).isEmpty();
    }

    @Test
    void 이모지_2개까지만_허용_초과시_에러() {
        TwoEmojiAllowedInput input = new TwoEmojiAllowedInput("🔥❤️👍");
        Set<ConstraintViolation<TwoEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("유효하지 않은");
    }

    @Test
    void 텍스트_입력은_실패() {
        OnlyOneEmojiAllowedInput input = new OnlyOneEmojiAllowedInput("hello");
        Set<ConstraintViolation<OnlyOneEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).hasSize(1);
    }

    @Test
    void 빈_문자열은_실패() {
        OnlyOneEmojiAllowedInput input = new OnlyOneEmojiAllowedInput("");
        Set<ConstraintViolation<OnlyOneEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).hasSize(1);
    }

    @Test
    void 공백_문자열은_실패() {
        OnlyOneEmojiAllowedInput input = new OnlyOneEmojiAllowedInput(" ");
        Set<ConstraintViolation<OnlyOneEmojiAllowedInput>> violations = validator.validate(input);
        assertThat(violations).hasSize(1);
    }

    private static class OnlyOneEmojiAllowedInput {
        @ValidEmoji(max = 1, message = "유효하지 않은 이모지입니다.")
        private String emoji;

        public OnlyOneEmojiAllowedInput(String emoji) {
            this.emoji = emoji;
        }

        public String getEmoji() {
            return emoji;
        }
    }

    private static class TwoEmojiAllowedInput {
        @ValidEmoji(max = 2)
        private String emoji;

        public TwoEmojiAllowedInput(String emoji) {
            this.emoji = emoji;
        }
    }
}

