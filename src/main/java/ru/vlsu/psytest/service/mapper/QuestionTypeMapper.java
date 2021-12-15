package ru.vlsu.psytest.service.mapper;


import ru.vlsu.psytest.domain.*;
import ru.vlsu.psytest.service.dto.QuestionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionType} and its DTO {@link QuestionTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionTypeMapper extends EntityMapper<QuestionTypeDTO, QuestionType> {



    default QuestionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionType questionType = new QuestionType();
        questionType.setId(id);
        return questionType;
    }
}
