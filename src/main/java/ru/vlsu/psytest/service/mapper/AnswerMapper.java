package ru.vlsu.psytest.service.mapper;


import ru.vlsu.psytest.domain.*;
import ru.vlsu.psytest.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ResultTestMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "resultTest.id", target = "resultTestId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "resultTestId", target = "resultTest")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
