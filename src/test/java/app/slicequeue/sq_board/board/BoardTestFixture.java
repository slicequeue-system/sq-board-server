package app.slicequeue.sq_board.board;


import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BoardTestFixture {

    public static Board createBoardExample1() {
        return Board.create("게시글1", 1L, 1L, null);
    }


    public static List<Board> createBoardExamples(int count, long projectId, long adminId) {
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            boards.add(Board.create(String.format("게시판%s", i), projectId, adminId, String.format("게시판%s 설명", i)));
        }
        return boards;
    }

    public static BoardListItem createBoardListItemExample1() {
        Instant at = Instant.parse("2025-04-15T13:31:42Z");
        return new BoardListItem(1L,"게시글1", 1L, 1L, at, at);
    }
}
