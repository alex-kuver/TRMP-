import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './question-type.reducer';
import { IQuestionType } from 'app/shared/model/question-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const QuestionType = (props: IQuestionTypeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { questionTypeList, match, loading } = props;
  return (
    <div>
      <h2 id="question-type-heading">
        <Translate contentKey="psychologicallyTestBackendApp.questionType.home.title">Question Types</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="psychologicallyTestBackendApp.questionType.home.createLabel">Create new Question Type</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {questionTypeList && questionTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.coefficient">Coefficient</Translate>
                </th>
                <th>
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {questionTypeList.map((questionType, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${questionType.id}`} color="link" size="sm">
                      {questionType.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`psychologicallyTestBackendApp.Psychotype.${questionType.type}`} />
                  </td>
                  <td>{questionType.coefficient}</td>
                  <td>{questionType.description}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${questionType.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questionType.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questionType.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="psychologicallyTestBackendApp.questionType.home.notFound">No Question Types found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ questionType }: IRootState) => ({
  questionTypeList: questionType.entities,
  loading: questionType.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionType);
