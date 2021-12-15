import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './result-test.reducer';
import { IResultTest } from 'app/shared/model/result-test.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResultTestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResultTestUpdate = (props: IResultTestUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resultTestEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/result-test' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.finishedAt = convertDateTimeToServer(values.finishedAt);

    if (errors.length === 0) {
      const entity = {
        ...resultTestEntity,
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
          <h2 id="psychologicallyTestBackendApp.resultTest.home.createOrEditLabel">
            <Translate contentKey="psychologicallyTestBackendApp.resultTest.home.createOrEditLabel">Create or edit a ResultTest</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resultTestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="result-test-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="result-test-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="finishedAtLabel" for="result-test-finishedAt">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.finishedAt">Finished At</Translate>
                </Label>
                <AvInput
                  id="result-test-finishedAt"
                  type="datetime-local"
                  className="form-control"
                  name="finishedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.resultTestEntity.finishedAt)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="demonstrativeTypeLabel" for="result-test-demonstrativeType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.demonstrativeType">Demonstrative Type</Translate>
                </Label>
                <AvField
                  id="result-test-demonstrativeType"
                  type="string"
                  className="form-control"
                  name="demonstrativeType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="stuckTypeLabel" for="result-test-stuckType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.stuckType">Stuck Type</Translate>
                </Label>
                <AvField
                  id="result-test-stuckType"
                  type="string"
                  className="form-control"
                  name="stuckType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="pedanticTypeLabel" for="result-test-pedanticType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.pedanticType">Pedantic Type</Translate>
                </Label>
                <AvField
                  id="result-test-pedanticType"
                  type="string"
                  className="form-control"
                  name="pedanticType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="excitableTypeLabel" for="result-test-excitableType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.excitableType">Excitable Type</Translate>
                </Label>
                <AvField
                  id="result-test-excitableType"
                  type="string"
                  className="form-control"
                  name="excitableType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="hyperthymicTypeLabel" for="result-test-hyperthymicType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.hyperthymicType">Hyperthymic Type</Translate>
                </Label>
                <AvField
                  id="result-test-hyperthymicType"
                  type="string"
                  className="form-control"
                  name="hyperthymicType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dysthymicTypeLabel" for="result-test-dysthymicType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.dysthymicType">Dysthymic Type</Translate>
                </Label>
                <AvField
                  id="result-test-dysthymicType"
                  type="string"
                  className="form-control"
                  name="dysthymicType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="anxiouslyFearfulTypeLabel" for="result-test-anxiouslyFearfulType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.anxiouslyFearfulType">Anxiously Fearful Type</Translate>
                </Label>
                <AvField
                  id="result-test-anxiouslyFearfulType"
                  type="string"
                  className="form-control"
                  name="anxiouslyFearfulType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emotionallyExaltedTypeLabel" for="result-test-emotionallyExaltedType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.emotionallyExaltedType">
                    Emotionally Exalted Type
                  </Translate>
                </Label>
                <AvField
                  id="result-test-emotionallyExaltedType"
                  type="string"
                  className="form-control"
                  name="emotionallyExaltedType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emotiveTypeLabel" for="result-test-emotiveType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.emotiveType">Emotive Type</Translate>
                </Label>
                <AvField
                  id="result-test-emotiveType"
                  type="string"
                  className="form-control"
                  name="emotiveType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cyclothymicTypeLabel" for="result-test-cyclothymicType">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.cyclothymicType">Cyclothymic Type</Translate>
                </Label>
                <AvField id="result-test-cyclothymicType" type="string" className="form-control" name="cyclothymicType" />
              </AvGroup>
              <AvGroup>
                <Label for="result-test-user">
                  <Translate contentKey="psychologicallyTestBackendApp.resultTest.user">User</Translate>
                </Label>
                <AvInput id="result-test-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/result-test" replace color="info">
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
  users: storeState.userManagement.users,
  resultTestEntity: storeState.resultTest.entity,
  loading: storeState.resultTest.loading,
  updating: storeState.resultTest.updating,
  updateSuccess: storeState.resultTest.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResultTestUpdate);
