import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-customer.reducer';

export const BankCustomerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankCustomerEntity = useAppSelector(state => state.bankCustomer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankCustomerDetailsHeading">
          <Translate contentKey="letsBankApp.bankCustomer.detail.title">BankCustomer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="letsBankApp.bankCustomer.id">Id</Translate>
            </span>
          </dt>
          <dd>{bankCustomerEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="letsBankApp.bankCustomer.email">Email</Translate>
            </span>
          </dt>
          <dd>{bankCustomerEntity.email}</dd>
          <dt>
            <span id="pinCode">
              <Translate contentKey="letsBankApp.bankCustomer.pinCode">Pin Code</Translate>
            </span>
          </dt>
          <dd>{bankCustomerEntity.pinCode}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="letsBankApp.bankCustomer.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bankCustomerEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="letsBankApp.bankCustomer.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bankCustomerEntity.createdDate ? (
              <TextFormat
                value={bankCustomerEntity.createdDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="letsBankApp.bankCustomer.lastModifiedBy">
                Last Modified By
              </Translate>
            </span>
          </dt>
          <dd>{bankCustomerEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="letsBankApp.bankCustomer.lastModifiedDate">
                Last Modified Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankCustomerEntity.lastModifiedDate ? (
              <TextFormat
                value={bankCustomerEntity.lastModifiedDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="letsBankApp.bankCustomer.person">Person</Translate>
          </dt>
          <dd>{bankCustomerEntity.person ? bankCustomerEntity.person.id : ''}</dd>
        </dl>
        <Button
          tag={Link}
          to="/bank-customer"
          replace
          color="info"
          data-cy="entityDetailsBackButton"
        >
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/bank-customer/${bankCustomerEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankCustomerDetail;
