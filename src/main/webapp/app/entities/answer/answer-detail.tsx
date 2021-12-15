import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnswerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnswerDetail = (props: IAnswerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { answerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="psychologicallyTestBackendApp.answer.detail.title">Answer</Translate> [<b>{answerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="answer">
              <Translate contentKey="psychologicallyTestBackendApp.answer.answer">Answer</Translate>
            </span>
          </dt>
          <dd>{answerEntity.answer ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="psychologicallyTestBackendApp.answer.question">Question</Translate>
          </dt>
          <dd>{answerEntity.questionId ? answerEntity.questionId : ''}</dd>
          <dt>
            <Translate contentKey="psychologicallyTestBackendApp.answer.resultTest">Result Test</Translate>
          </dt>
          <dd>{answerEntity.resultTestId ? answerEntity.resultTestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer/${answerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ answer }: IRootState) => ({
  answerEntity: answer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnswerDetail);
