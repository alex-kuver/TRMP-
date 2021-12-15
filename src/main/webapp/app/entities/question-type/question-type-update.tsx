import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './question-type.reducer';
import { IQuestionType } from 'app/shared/model/question-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuestionTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionTypeUpdate = (props: IQuestionTypeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { questionTypeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/question-type');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...questionTypeEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="psychologicallyTestBackendApp.questionType.home.createOrEditLabel">
            <Translate contentKey="psychologicallyTestBackendApp.questionType.home.createOrEditLabel">
              Create or edit a QuestionType
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : questionTypeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="question-type-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="question-type-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="question-type-type">
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.type">Type</Translate>
                </Label>
                <AvInput
                  id="question-type-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && questionTypeEntity.type) || 'DEMONSTRATIVE_TYPE'}
                >
                  <option value="DEMONSTRATIVE_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.DEMONSTRATIVE_TYPE')}</option>
                  <option value="STUCK_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.STUCK_TYPE')}</option>
                  <option value="PEDANTIC_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.PEDANTIC_TYPE')}</option>
                  <option value="EXCITABLE_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.EXCITABLE_TYPE')}</option>
                  <option value="HYPERTHYMIC_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.HYPERTHYMIC_TYPE')}</option>
                  <option value="DYSTHYMIC_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.DYSTHYMIC_TYPE')}</option>
                  <option value="ANXIOUSLY_FEARFULL_TYPE">
                    {translate('psychologicallyTestBackendApp.Psychotype.ANXIOUSLY_FEARFULL_TYPE')}
                  </option>
                  <option value="EMOTIONALLY_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.EMOTIONALLY_TYPE')}</option>
                  <option value="EMOTIVE_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.EMOTIVE_TYPE')}</option>
                  <option value="CYCLOTHYMIC_TYPE">{translate('psychologicallyTestBackendApp.Psychotype.CYCLOTHYMIC_TYPE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="coefficientLabel" for="question-type-coefficient">
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.coefficient">Coefficient</Translate>
                </Label>
                <AvField
                  id="question-type-coefficient"
                  type="string"
                  className="form-control"
                  name="coefficient"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="question-type-description">
                  <Translate contentKey="psychologicallyTestBackendApp.questionType.description">Description</Translate>
                </Label>
                <AvField
                  id="question-type-description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/question-type" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  questionTypeEntity: storeState.questionType.entity,
  loading: storeState.questionType.loading,
  updating: storeState.questionType.updating,
  updateSuccess: storeState.questionType.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionTypeUpdate);
