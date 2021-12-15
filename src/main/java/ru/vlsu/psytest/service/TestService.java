package ru.vlsu.psytest.service;

import ru.vlsu.psytest.service.dto.AnswerDTO;
import ru.vlsu.psytest.service.dto.ResultTestDTO;

import java.util.List;

public interface TestService {

    ResultTestDTO solveTest(List<AnswerDTO> answers, String username);
}
