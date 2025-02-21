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

import { createEntity, getEntity, reset, updateEntity } from './bank-teller.reducer';

export const BankTellerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankTellerEntity = useAppSelector(state => state.bankTeller.entity);
  const loading = useAppSelector(state => state.bankTeller.loading);
  const updating = useAppSelector(state => state.bankTeller.updating);
  const updateSuccess = useAppSelector(state => state.bankTeller.updateSuccess);

  const handleClose = () => {
    navigate(`/bank-teller${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...bankTellerEntity,
      ...values,
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
          ...bankTellerEntity,
          createdDate: convertDateTimeFromServer(bankTellerEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(bankTellerEntity.lastModifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="letsBankApp.bankTeller.home.createOrEditLabel"
            data-cy="BankTellerCreateUpdateHeading"
          >
            <Translate contentKey="letsBankApp.bankTeller.home.createOrEditLabel">
              Create or edit a BankTeller
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
                  id="bank-teller-id"
                  label={translate('letsBankApp.bankTeller.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('letsBankApp.bankTeller.employeeId')}
                id="bank-teller-employeeId"
                name="employeeId"
                data-cy="employeeId"
                type="text"
                validate={{
                  maxLength: {
                    value: 64,
                    message: translate('entity.validation.maxlength', { max: 64 }),
                  },
                }}
              />
              <ValidatedField
                label={translate('letsBankApp.bankTeller.thaiName')}
                id="bank-teller-thaiName"
                name="thaiName"
                data-cy="thaiName"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTeller.englishName')}
                id="bank-teller-englishName"
                name="englishName"
                data-cy="englishName"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTeller.createdBy')}
                id="bank-teller-createdBy"
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
                label={translate('letsBankApp.bankTeller.createdDate')}
                id="bank-teller-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankTeller.lastModifiedBy')}
                id="bank-teller-lastModifiedBy"
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
                label={translate('letsBankApp.bankTeller.lastModifiedDate')}
                id="bank-teller-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/bank-teller"
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

export default BankTellerUpdate;
