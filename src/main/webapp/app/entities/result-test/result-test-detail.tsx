import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './result-test.reducer';
import { IResultTest } from 'app/shared/model/result-test.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResultTestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResultTestDetail = (props: IResultTestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { resultTestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="psychologicallyTestBackendApp.resultTest.detail.title">ResultTest</Translate> [<b>{resultTestEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="finishedAt">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {resultTestEntity.finishedAt ? <TextFormat value={resultTestEntity.finishedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="demonstrativeType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.demonstrativeType">Demonstrative Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.demonstrativeType}</dd>
          <dt>
            <span id="stuckType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.stuckType">Stuck Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.stuckType}</dd>
          <dt>
            <span id="pedanticType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.pedanticType">Pedantic Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.pedanticType}</dd>
          <dt>
            <span id="excitableType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.excitableType">Excitable Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.excitableType}</dd>
          <dt>
            <span id="hyperthymicType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.hyperthymicType">Hyperthymic Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.hyperthymicType}</dd>
          <dt>
            <span id="dysthymicType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.dysthymicType">Dysthymic Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.dysthymicType}</dd>
          <dt>
            <span id="anxiouslyFearfulType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.anxiouslyFearfulType">Anxiously Fearful Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.anxiouslyFearfulType}</dd>
          <dt>
            <span id="emotionallyExaltedType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.emotionallyExaltedType">Emotionally Exalted Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.emotionallyExaltedType}</dd>
          <dt>
            <span id="emotiveType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.emotiveType">Emotive Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.emotiveType}</dd>
          <dt>
            <span id="cyclothymicType">
              <Translate contentKey="psychologicallyTestBackendApp.resultTest.cyclothymicType">Cyclothymic Type</Translate>
            </span>
          </dt>
          <dd>{resultTestEntity.cyclothymicType}</dd>
          <dt>
            <Translate contentKey="psychologicallyTestBackendApp.resultTest.user">User</Translate>
          </dt>
          <dd>{resultTestEntity.userId ? resultTestEntity.userId : ''}</dd>
        </dl>
        <Button tag={Link} to="/result-test" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/result-test/${resultTestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ resultTest }: IRootState) => ({
  resultTestEntity: resultTest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResultTestDetail);
