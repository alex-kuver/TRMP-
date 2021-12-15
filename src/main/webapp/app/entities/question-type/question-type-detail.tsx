import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './question-type.reducer';
import { IQuestionType } from 'app/shared/model/question-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionTypeDetail = (props: IQuestionTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { questionTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="psychologicallyTestBackendApp.questionType.detail.title">QuestionType</Translate> [
          <b>{questionTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="psychologicallyTestBackendApp.questionType.type">Type</Translate>
            </span>
          </dt>
          <dd>{questionTypeEntity.type}</dd>
          <dt>
            <span id="coefficient">
              <Translate contentKey="psychologicallyTestBackendApp.questionType.coefficient">Coefficient</Translate>
            </span>
          </dt>
          <dd>{questionTypeEntity.coefficient}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="psychologicallyTestBackendApp.questionType.description">Description</Translate>
            </span>
          </dt>
          <dd>{questionTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/question-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question-type/${questionTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ questionType }: IRootState) => ({
  questionTypeEntity: questionType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionTypeDetail);
