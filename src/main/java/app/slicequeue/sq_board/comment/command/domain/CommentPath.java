package app.slicequeue.sq_board.comment.command.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentPath {

    private String path;

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 한글자에 올 62 진수 만들 알파벳들 숫자 조합 0 -> z

    private static final int DEPTH_CHUNK_SIZE = 5;   // 경로 정보 1댑스당 5자리로 표시
    private static final int MAX_DEPTH = 5;          // 최대 경로는 5 뎁스 까지

    // "00000" ~ "zzzzz"
    private static final String MIN_CHUNK = String.valueOf(CHARSET.charAt(0)).repeat(DEPTH_CHUNK_SIZE); // 00000
    private static final String MAX_CHUNK =
            String.valueOf(CHARSET.charAt(CHARSET.length() - 1)).repeat(DEPTH_CHUNK_SIZE); // zzzzz

    public static CommentPath create(String path) {
        if (isDepthOverflowed(path)) {
            throw new IllegalStateException("depth overflowed");
        }
        CommentPath commentPath = new CommentPath();
        commentPath.path = path;
        return commentPath;
    }

    private static boolean isDepthOverflowed(String path) {
        return calDepth(path) > MAX_DEPTH;
    }

    private static int calDepth(String path) {
        return path.length() / DEPTH_CHUNK_SIZE;
    }

    public int getDepth() {
        return calDepth(path);
    }

    public boolean isRoot() {
        return calDepth(path) == 1;
    }

    public String getParentPath() {
        return path.substring(0, path.length() - DEPTH_CHUNK_SIZE);
    }

    public CommentPath createChildCommentPath(CommentPath descendantsTopPath) {
        return createChildCommentPath(
                descendantsTopPath != null ? descendantsTopPath.getPath() : null);
    }

    public CommentPath createChildCommentPath(String descendantsTopPath) {
        if (descendantsTopPath == null) {
            return CommentPath.create(path + MIN_CHUNK);
        }
        String childrenTopPath = findChildrenTopPath(descendantsTopPath);
        return CommentPath.create(increase(childrenTopPath));
    }

    private String findChildrenTopPath(String descendantsTopPath) {
        return descendantsTopPath.substring(0, (getDepth() + 1) * DEPTH_CHUNK_SIZE);
    }

    private String increase(String path) {
        // 00000 00000
        String lastChunk = path.substring(path.length() - DEPTH_CHUNK_SIZE);
        if (isChunkOverflowed(lastChunk)) {
            throw new IllegalStateException("chunk overflowed");
        }
        int charsetLength = CHARSET.length();
        int value = 0;
        for (char ch : lastChunk.toCharArray()) {
            value = value * charsetLength + CHARSET.indexOf(ch); // 문자열 62진수를 정수값으로 변환
            // 마지막 청크 문자를 정수 값으로 누적 환산
        }

        value = value + 1; // 1 증가 시키기

        String result = "";
        for (int i=0; i < DEPTH_CHUNK_SIZE; i++) {
            result = CHARSET.charAt(value % charsetLength) + result; // 다시 정수를 62 진수로 전환 나머지로 문자열 변환
            value /= charsetLength; // 정수에서 62진수 한자리 진행한 것 반영
        }

        return path.substring(0, path.length() - DEPTH_CHUNK_SIZE) + result; // 끝에 다시 붙이기
    }

    private boolean isChunkOverflowed(String lastChunk) {
        return MAX_CHUNK.equals(lastChunk);
    }
}
