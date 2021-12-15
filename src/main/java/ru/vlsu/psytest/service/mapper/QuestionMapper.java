package ru.vlsu.psytest.service.mapper;


import ru.vlsu.psytest.domain.*;
import ru.vlsu.psytest.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionTypeMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "questionType.id", target = "questionTypeId")
    QuestionDTO toDto(Question question);

    @Mapping(source = "questionTypeId", target = "questionType")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
