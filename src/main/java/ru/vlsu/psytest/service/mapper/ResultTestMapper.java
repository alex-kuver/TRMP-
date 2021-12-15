package ru.vlsu.psytest.service.mapper;


import ru.vlsu.psytest.domain.*;
import ru.vlsu.psytest.service.dto.ResultTestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultTest} and its DTO {@link ResultTestDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ResultTestMapper extends EntityMapper<ResultTestDTO, ResultTest> {

    @Mapping(source = "user.id", target = "userId")
    ResultTestDTO toDto(ResultTest resultTest);

    @Mapping(source = "userId", target = "user")
    ResultTest toEntity(ResultTestDTO resultTestDTO);

    default ResultTest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResultTest resultTest = new ResultTest();
        resultTest.setId(id);
        return resultTest;
    }
}
