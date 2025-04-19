package app.slicequeue.sq_board.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLimitCalculator {

    public static Long calculatePageLimit(long page, long pageSize, int movablePageCount) {
        return (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
    }

}
