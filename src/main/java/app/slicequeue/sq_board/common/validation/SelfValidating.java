package app.slicequeue.sq_board.common.validation;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {

    private static final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    protected void validateSelf() {
        @SuppressWarnings("unchecked")
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
