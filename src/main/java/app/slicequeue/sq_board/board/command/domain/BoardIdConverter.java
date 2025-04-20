package app.slicequeue.sq_board.board.command.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter //(autoApply = true)
public class BoardIdConverter implements AttributeConverter<BoardId, Long> {
    @Override
    public Long convertToDatabaseColumn(BoardId attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public BoardId convertToEntityAttribute(Long dbData) {
        return dbData != null ? BoardId.from(dbData) : null;
    }
}
