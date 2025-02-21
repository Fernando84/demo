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
import { createEntity, getEntity, reset, updateEntity } from './bank-customer.reducer';

export const BankCustomerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankPeople = useAppSelector(state => state.bankPerson.entities);
  const bankCustomerEntity = useAppSelector(state => state.bankCustomer.entity);
  const loading = useAppSelector(state => state.bankCustomer.loading);
  const updating = useAppSelector(state => state.bankCustomer.updating);
  const updateSuccess = useAppSelector(state => state.bankCustomer.updateSuccess);

  const handleClose = () => {
    navigate(`/bank-customer${location.search}`);
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    const entity = {
      ...bankCustomerEntity,
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
          ...bankCustomerEntity,
          createdDate: convertDateTimeFromServer(bankCustomerEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(bankCustomerEntity.lastModifiedDate),
          person: bankCustomerEntity?.person?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="letsBankApp.bankCustomer.home.createOrEditLabel"
            data-cy="BankCustomerCreateUpdateHeading"
          >
            <Translate contentKey="letsBankApp.bankCustomer.home.createOrEditLabel">
              Create or edit a BankCustomer
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
                  id="bank-customer-id"
                  label={translate('letsBankApp.bankCustomer.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('letsBankApp.bankCustomer.email')}
                id="bank-customer-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  maxLength: {
                    value: 250,
                    message: translate('entity.validation.maxlength', { max: 250 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankCustomer.pinCode')}
                id="bank-customer-pinCode"
                name="pinCode"
                data-cy="pinCode"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankCustomer.createdBy')}
                id="bank-customer-createdBy"
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
                label={translate('letsBankApp.bankCustomer.createdDate')}
                id="bank-customer-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankCustomer.lastModifiedBy')}
                id="bank-customer-lastModifiedBy"
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
                label={translate('letsBankApp.bankCustomer.lastModifiedDate')}
                id="bank-customer-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="bank-customer-person"
                name="person"
                data-cy="person"
                label={translate('letsBankApp.bankCustomer.person')}
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
                to="/bank-customer"
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

export default BankCustomerUpdate;
