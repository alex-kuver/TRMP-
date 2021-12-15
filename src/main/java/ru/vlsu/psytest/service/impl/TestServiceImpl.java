package ru.vlsu.psytest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.psytest.domain.User;
import ru.vlsu.psytest.service.*;
import ru.vlsu.psytest.service.dto.*;
import ru.vlsu.psytest.web.rest.AnswerResource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final Logger log = LoggerFactory.getLogger(AnswerResource.class);

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final ResultTestService resultTestService;

    private final QuestionTypeService questionTypeService;

    private final UserService userService;

    public TestServiceImpl(QuestionService questionService, AnswerService answerService, ResultTestService resultTestService, QuestionTypeService questionTypeService, UserService userService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.resultTestService = resultTestService;
        this.questionTypeService = questionTypeService;
        this.userService = userService;
    }

    @Override
    public ResultTestDTO solveTest(List<AnswerDTO> answers, String username) {
        log.debug("Request to save solve for test");

        List<QuestionDTO> questions = questionService.findAll();

        if (answers.size() != questions.size()) {
            //TODO throw exception
            return null;
        }
        ResultTestDTO result = new ResultTestDTO(0,0,0,0,0,0,0,0,0,0,null);
        if (username != "ANONIM") {
            Optional<UserDTO> user = userService.findUserByUserName(username);
            result.setUserId(user.get().getId());
        }

        for (AnswerDTO answer: answers) {
            Optional<QuestionDTO> question = questionService.findOne(answer.getQuestionId());
            Optional<QuestionTypeDTO> questionType = questionTypeService.findOne(question.get().getQuestionTypeId());

            switch (questionType.get().getType()) {
                case DEMONSTRATIVE_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setDemonstrativeType(result.getDysthymicType() + questionType.get().getCoefficient());
                        } else {
                            result.setDemonstrativeType(result.getDysthymicType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case STUCK_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setStuckType(result.getStuckType() + questionType.get().getCoefficient());
                        } else {
                            result.setStuckType(result.getStuckType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case PEDANTIC_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setPedanticType(result.getPedanticType() + questionType.get().getCoefficient());
                        } else {
                            result.setPedanticType(result.getPedanticType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case EXCITABLE_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setExcitableType(result.getExcitableType() + questionType.get().getCoefficient());
                        } else {
                            result.setExcitableType(result.getExcitableType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case HYPERTHYMIC_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setHyperthymicType(result.getHyperthymicType() + questionType.get().getCoefficient());
                        } else {
                            result.setHyperthymicType(result.getHyperthymicType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case DYSTHYMIC_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setDysthymicType(result.getDysthymicType() + questionType.get().getCoefficient());
                        } else {
                            result.setDysthymicType(result.getDysthymicType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case ANXIOUSLY_FEARFULL_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setAnxiouslyFearfulType(result.getAnxiouslyFearfulType() + questionType.get().getCoefficient());
                        } else {
                            result.setAnxiouslyFearfulType(result.getAnxiouslyFearfulType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case CYCLOTHYMIC_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setCyclothymicType(result.getCyclothymicType() + questionType.get().getCoefficient());
                        } else {
                            result.setCyclothymicType(result.getCyclothymicType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case EMOTIVE_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setEmotiveType(result.getEmotiveType() + questionType.get().getCoefficient());
                        } else {
                            result.setEmotiveType(result.getEmotiveType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
                case EMOTIONALLY_TYPE:
                    if (answer.isAnswer()) {
                        if (question.get().isIsAdd()) {
                            result.setEmotionallyExaltedType(result.getEmotionallyExaltedType() + questionType.get().getCoefficient());
                        } else {
                            result.setEmotionallyExaltedType(result.getEmotionallyExaltedType() - questionType.get().getCoefficient());
                        }
                    }
                    break;
            }
        }
        result.setFinishedAt(Instant.now());
        result = resultTestService.save(result);
        for (AnswerDTO answer: answers) {
            answer.setResultTestId(result.getId());
            answerService.save(answer);
        }
        return result;
    }
}
