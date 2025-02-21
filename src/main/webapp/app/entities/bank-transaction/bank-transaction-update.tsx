import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import {
  convertDateTimeFromServer,
  convertDateTimeToServer,
  displayDefaultDateTime,
} from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBankAccounts } from 'app/entities/bank-account/bank-account.reducer';
import { createEntity, getEntity, reset, updateEntity } from './bank-transaction.reducer';

export const BankTransactionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankAccounts = useAppSelector(state => state.bankAccount.entities);
  const bankTransactionEntity = useAppSelector(state => state.bankTransaction.entity);
  const loading = useAppSelector(state => state.bankTransaction.loading);
  const updating = useAppSelector(state => state.bankTransaction.updating);
  const updateSuccess = useAppSelector(state => state.bankTransaction.updateSuccess);

  const handleClose = () => {
    navigate(`/bank-transaction${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBankAccounts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    if (values.balance !== undefined && typeof values.balance !== 'number') {
      values.balance = Number(values.balance);
    }
    values.transactionDate = convertDateTimeToServer(values.transactionDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    const entity = {
      ...bankTransactionEntity,
      ...values,
      account: bankAccounts.find(it => it.id.toString() === values.account?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          transactionDate: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
        }
      : {
          ...bankTransactionEntity,
          transactionDate: convertDateTimeFromServer(bankTransactionEntity.transactionDate),
          createdDate: convertDateTimeFromServer(bankTransactionEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(bankTransactionEntity.lastModifiedDate),
          account: bankTransactionEntity?.account?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="letsBankApp.bankTransaction.home.createOrEditLabel"
            data-cy="BankTransactionCreateUpdateHeading"
          >
            <Translate contentKey="letsBankApp.bankTransaction.home.createOrEditLabel">
              Create or edit a BankTransaction
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bank-transaction-id"
                  label={translate('letsBankApp.bankTransaction.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.code')}
                id="bank-transaction-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.channel')}
                id="bank-transaction-channel"
                name="channel"
                data-cy="channel"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.amount')}
                id="bank-transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.balance')}
                id="bank-transaction-balance"
                name="balance"
                data-cy="balance"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.targetAccountId')}
                id="bank-transaction-targetAccountId"
                name="targetAccountId"
                data-cy="targetAccountId"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.transactionDate')}
                id="bank-transaction-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.remark')}
                id="bank-transaction-remark"
                name="remark"
                data-cy="remark"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.createdBy')}
                id="bank-transaction-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.createdDate')}
                id="bank-transaction-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.lastModifiedBy')}
                id="bank-transaction-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTransaction.lastModifiedDate')}
                id="bank-transaction-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="bank-transaction-account"
                name="account"
                data-cy="account"
                label={translate('letsBankApp.bankTransaction.account')}
                type="select"
              >
                <option value="" key="0" />
                {bankAccounts
                  ? bankAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/bank-transaction"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button
                color="primary"
                id="save-entity"
                data-cy="entityCreateSaveButton"
                type="submit"
                disabled={updating}
              >
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BankTransactionUpdate;
