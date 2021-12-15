import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './question.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionDetail = (props: IQuestionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { questionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="psychologicallyTestBackendApp.question.detail.title">Question</Translate> [<b>{questionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="question">
              <Translate contentKey="psychologicallyTestBackendApp.question.question">Question</Translate>
            </span>
          </dt>
          <dd>{questionEntity.question}</dd>
          <dt>
            <span id="isAdd">
              <Translate contentKey="psychologicallyTestBackendApp.question.isAdd">Is Add</Translate>
            </span>
          </dt>
          <dd>{questionEntity.isAdd ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="psychologicallyTestBackendApp.question.questionType">Question Type</Translate>
          </dt>
          <dd>{questionEntity.questionTypeId ? questionEntity.questionTypeId : ''}</dd>
        </dl>
        <Button tag={Link} to="/question" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question/${questionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ question }: IRootState) => ({
  questionEntity: question.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionDetail);
