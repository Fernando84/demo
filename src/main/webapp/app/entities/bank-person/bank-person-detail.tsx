import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-person.reducer';

export const BankPersonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankPersonEntity = useAppSelector(state => state.bankPerson.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankPersonDetailsHeading">
          <Translate contentKey="letsBankApp.bankPerson.detail.title">BankPerson</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="letsBankApp.bankPerson.id">Id</Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.id}</dd>
          <dt>
            <span id="citizenId">
              <Translate contentKey="letsBankApp.bankPerson.citizenId">Citizen Id</Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.citizenId}</dd>
          <dt>
            <span id="thaiName">
              <Translate contentKey="letsBankApp.bankPerson.thaiName">Thai Name</Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.thaiName}</dd>
          <dt>
            <span id="englishName">
              <Translate contentKey="letsBankApp.bankPerson.englishName">English Name</Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.englishName}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="letsBankApp.bankPerson.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="letsBankApp.bankPerson.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bankPersonEntity.createdDate ? (
              <TextFormat
                value={bankPersonEntity.createdDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="letsBankApp.bankPerson.lastModifiedBy">
                Last Modified By
              </Translate>
            </span>
          </dt>
          <dd>{bankPersonEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="letsBankApp.bankPerson.lastModifiedDate">
                Last Modified Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankPersonEntity.lastModifiedDate ? (
              <TextFormat
                value={bankPersonEntity.lastModifiedDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bank-person" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-person/${bankPersonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankPersonDetail;
