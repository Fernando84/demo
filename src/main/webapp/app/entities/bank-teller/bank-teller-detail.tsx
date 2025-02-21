import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-teller.reducer';

export const BankTellerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankTellerEntity = useAppSelector(state => state.bankTeller.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankTellerDetailsHeading">
          <Translate contentKey="letsBankApp.bankTeller.detail.title">BankTeller</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="letsBankApp.bankTeller.id">Id</Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.id}</dd>
          <dt>
            <span id="employeeId">
              <Translate contentKey="letsBankApp.bankTeller.employeeId">Employee Id</Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.employeeId}</dd>
          <dt>
            <span id="thaiName">
              <Translate contentKey="letsBankApp.bankTeller.thaiName">Thai Name</Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.thaiName}</dd>
          <dt>
            <span id="englishName">
              <Translate contentKey="letsBankApp.bankTeller.englishName">English Name</Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.englishName}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="letsBankApp.bankTeller.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="letsBankApp.bankTeller.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bankTellerEntity.createdDate ? (
              <TextFormat
                value={bankTellerEntity.createdDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="letsBankApp.bankTeller.lastModifiedBy">
                Last Modified By
              </Translate>
            </span>
          </dt>
          <dd>{bankTellerEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="letsBankApp.bankTeller.lastModifiedDate">
                Last Modified Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankTellerEntity.lastModifiedDate ? (
              <TextFormat
                value={bankTellerEntity.lastModifiedDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bank-teller" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-teller/${bankTellerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankTellerDetail;
