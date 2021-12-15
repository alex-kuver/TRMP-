import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnswerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Answer = (props: IAnswerProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { answerList, match, loading } = props;
  return (
    <div>
      <h2 id="answer-heading">
        <Translate contentKey="psychologicallyTestBackendApp.answer.home.title">Answers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="psychologicallyTestBackendApp.answer.home.createLabel">Create new Answer</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {answerList && answerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.answer.answer">Answer</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.answer.question">Question</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.answer.resultTest">Result Test</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {answerList.map((answer, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${answer.id}`} color="link" size="sm">
                      {answer.id}
                    </Button>
                  </td>
                  <td>{answer.answer ? 'true' : 'false'}</td>
                  <td>{answer.questionId ? <Link to={`question/${answer.questionId}`}>{answer.questionId}</Link> : ''}</td>
                  <td>{answer.resultTestId ? <Link to={`result-test/${answer.resultTestId}`}>{answer.resultTestId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${answer.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${answer.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${answer.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="psychologicallyTestBackendApp.answer.home.notFound">No Answers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ answer }: IRootState) => ({
  answerList: answer.entities,
  loading: answer.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Answer);
