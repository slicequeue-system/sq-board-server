package app.slicequeue.sq_board.board.query.infra;

import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBoardPagingQueryRepository extends JpaRepository<Board, BoardId> {

    @Query("""
                SELECT new app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem(
                    b.boardId.id,
                    b.name,
                    b.projectId,
                    b.adminId,
                    b.createdAt,
                    b.updatedAt
                )
                FROM Board b
                WHERE b.projectId = :projectId
            """)
    Page<BoardListItem> findAllBoardListItemsByProjectId(@Param("projectId") Long projectId, Pageable pageable);

    @Query("""
            SELECT new app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem(
                b.boardId.id,
                b.name,
                b.projectId,
                b.adminId,
                b.createdAt,
                b.updatedAt
            )
            FROM Board b
            WHERE b.projectId = :projectId
            ORDER BY b.boardId.id desc LIMIT :pageSize
                """)
    List<BoardListItem> findAllBoardListItemsInfiniteScroll(Long projectId, Long pageSize);

    @Query("""
           SELECT new app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem(
                b.boardId.id,
                b.name,
                b.projectId,
                b.adminId,
                b.createdAt,
                b.updatedAt
            )
            FROM Board b
            WHERE b.projectId = :projectId
                  AND b.boardId.id < :lastBoardId
            ORDER BY b.boardId.id desc LIMIT :pageSize
            """)
    List<BoardListItem> findAllBoardListItemsInfiniteScroll(Long projectId, Long pageSize, Long lastBoardId);

}
