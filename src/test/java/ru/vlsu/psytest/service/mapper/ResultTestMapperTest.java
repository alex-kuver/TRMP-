package ru.vlsu.psytest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultTestMapperTest {

    private ResultTestMapper resultTestMapper;

    @BeforeEach
    public void setUp() {
        resultTestMapper = new ResultTestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resultTestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resultTestMapper.fromId(null)).isNull();
    }
}
