package app.slicequeue.sq_board.board.command.application;


import app.slicequeue.sq_board.board.command.domain.Board;

public class BoardFixture {

    public static Board createBoardExample1() {
        return Board.create("게시글1", 1L, 1L, null);
    }

}
