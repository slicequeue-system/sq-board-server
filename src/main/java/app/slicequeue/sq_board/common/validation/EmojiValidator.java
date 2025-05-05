package app.slicequeue.sq_board.common.validation;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmojiValidator implements ConstraintValidator<ValidEmoji, String> {

    private int maxEmojiCount;

    private static final String INVISIBLE_UNICODE_REGEX = "[\\uFE0F\\u200D]";

    @Override
    public void initialize(ValidEmoji constraintAnnotation) {
        this.maxEmojiCount = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;

        // 1. 이모지 추출
        List<String> emojis = EmojiParser.extractEmojis(value);
        if (emojis.isEmpty()) return false;

        // 2. 이모지 외 문자 제거 후 비가시 문자까지 제거
        String nonEmojiContent = EmojiParser.removeAllEmojis(value)
                .replaceAll(INVISIBLE_UNICODE_REGEX, "")
                .strip();

        if (!nonEmojiContent.isEmpty()) return false;

        // 3. 이모지 개수 제한
        return emojis.size() <= maxEmojiCount;
    }
}

