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

import { getEntities as getBankPeople } from 'app/entities/bank-person/bank-person.reducer';
import { createEntity, getEntity, reset, updateEntity } from './bank-account.reducer';

export const BankAccountUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankPeople = useAppSelector(state => state.bankPerson.entities);
  const bankAccountEntity = useAppSelector(state => state.bankAccount.entity);
  const loading = useAppSelector(state => state.bankAccount.loading);
  const updating = useAppSelector(state => state.bankAccount.updating);
  const updateSuccess = useAppSelector(state => state.bankAccount.updateSuccess);

  const handleClose = () => {
    navigate(`/bank-account${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBankPeople({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.balance !== undefined && typeof values.balance !== 'number') {
      values.balance = Number(values.balance);
    }
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    const entity = {
      ...bankAccountEntity,
      ...values,
      person: bankPeople.find(it => it.id.toString() === values.person?.toString()),
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
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
        }
      : {
          ...bankAccountEntity,
          createdDate: convertDateTimeFromServer(bankAccountEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(bankAccountEntity.lastModifiedDate),
          person: bankAccountEntity?.person?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="letsBankApp.bankAccount.home.createOrEditLabel"
            data-cy="BankAccountCreateUpdateHeading"
          >
            <Translate contentKey="letsBankApp.bankAccount.home.createOrEditLabel">
              Create or edit a BankAccount
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
                  id="bank-account-id"
                  label={translate('letsBankApp.bankAccount.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('letsBankApp.bankAccount.accountNumber')}
                id="bank-account-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankAccount.balance')}
                id="bank-account-balance"
                name="balance"
                data-cy="balance"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankAccount.createdBy')}
                id="bank-account-createdBy"
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
                label={translate('letsBankApp.bankAccount.createdDate')}
                id="bank-account-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankAccount.lastModifiedBy')}
                id="bank-account-lastModifiedBy"
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
                label={translate('letsBankApp.bankAccount.lastModifiedDate')}
                id="bank-account-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="bank-account-person"
                name="person"
                data-cy="person"
                label={translate('letsBankApp.bankAccount.person')}
                type="select"
              >
                <option value="" key="0" />
                {bankPeople
                  ? bankPeople.map(otherEntity => (
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
                to="/bank-account"
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

export default BankAccountUpdate;
