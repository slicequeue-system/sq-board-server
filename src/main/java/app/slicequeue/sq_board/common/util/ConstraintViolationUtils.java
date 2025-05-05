package app.slicequeue.sq_board.common.util;

import org.springframework.dao.DataIntegrityViolationException;

public final class ConstraintViolationUtils {

    private ConstraintViolationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isForeignKeyViolation(DataIntegrityViolationException e) {
        return e.getCause() != null &&
               e.getCause().getMessage() != null &&
               e.getCause().getMessage().contains("a foreign key constraint fails");
    }

    // 필요 시 제약 이름도 필터링 가능
    public static boolean isForeignKeyViolationOn(DataIntegrityViolationException e, String constraintName) {
        return isForeignKeyViolation(e) &&
               e.getCause().getMessage().contains(constraintName);
    }
}
