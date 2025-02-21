import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-account.reducer';

export const BankAccountDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankAccountEntity = useAppSelector(state => state.bankAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankAccountDetailsHeading">
          <Translate contentKey="letsBankApp.bankAccount.detail.title">BankAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="letsBankApp.bankAccount.id">Id</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.id}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="letsBankApp.bankAccount.accountNumber">
                Account Number
              </Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.accountNumber}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="letsBankApp.bankAccount.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.balance}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="letsBankApp.bankAccount.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="letsBankApp.bankAccount.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bankAccountEntity.createdDate ? (
              <TextFormat
                value={bankAccountEntity.createdDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="letsBankApp.bankAccount.lastModifiedBy">
                Last Modified By
              </Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="letsBankApp.bankAccount.lastModifiedDate">
                Last Modified Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankAccountEntity.lastModifiedDate ? (
              <TextFormat
                value={bankAccountEntity.lastModifiedDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="letsBankApp.bankAccount.person">Person</Translate>
          </dt>
          <dd>{bankAccountEntity.person ? bankAccountEntity.person.id : ''}</dd>
        </dl>
        <Button
          tag={Link}
          to="/bank-account"
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
          to={`/bank-account/${bankAccountEntity.id}/edit`}
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

export default BankAccountDetail;
