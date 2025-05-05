package app.slicequeue.sq_board.common.callback;

@FunctionalInterface
public interface LockedReturnCallback<T> {
    T execute();
}
