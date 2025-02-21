import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-transaction.reducer';

export const BankTransactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankTransactionEntity = useAppSelector(state => state.bankTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankTransactionDetailsHeading">
          <Translate contentKey="letsBankApp.bankTransaction.detail.title">
            BankTransaction
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="letsBankApp.bankTransaction.id">Id</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="letsBankApp.bankTransaction.code">Code</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.code}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="letsBankApp.bankTransaction.channel">Channel</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.channel}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="letsBankApp.bankTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.amount}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="letsBankApp.bankTransaction.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.balance}</dd>
          <dt>
            <span id="targetAccountId">
              <Translate contentKey="letsBankApp.bankTransaction.targetAccountId">
                Target Account Id
              </Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.targetAccountId}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="letsBankApp.bankTransaction.transactionDate">
                Transaction Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.transactionDate ? (
              <TextFormat
                value={bankTransactionEntity.transactionDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="remark">
              <Translate contentKey="letsBankApp.bankTransaction.remark">Remark</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.remark}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="letsBankApp.bankTransaction.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="letsBankApp.bankTransaction.createdDate">
                Created Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.createdDate ? (
              <TextFormat
                value={bankTransactionEntity.createdDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="letsBankApp.bankTransaction.lastModifiedBy">
                Last Modified By
              </Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="letsBankApp.bankTransaction.lastModifiedDate">
                Last Modified Date
              </Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.lastModifiedDate ? (
              <TextFormat
                value={bankTransactionEntity.lastModifiedDate}
                type="date"
                format={APP_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="letsBankApp.bankTransaction.account">Account</Translate>
          </dt>
          <dd>{bankTransactionEntity.account ? bankTransactionEntity.account.id : ''}</dd>
        </dl>
        <Button
          tag={Link}
          to="/bank-transaction"
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
          to={`/bank-transaction/${bankTransactionEntity.id}/edit`}
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

export default BankTransactionDetail;
