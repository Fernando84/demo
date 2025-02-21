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

import { createEntity, getEntity, reset, updateEntity } from './bank-person.reducer';

export const BankPersonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankPersonEntity = useAppSelector(state => state.bankPerson.entity);
  const loading = useAppSelector(state => state.bankPerson.loading);
  const updating = useAppSelector(state => state.bankPerson.updating);
  const updateSuccess = useAppSelector(state => state.bankPerson.updateSuccess);

  const handleClose = () => {
    navigate(`/bank-person${location.search}`);
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
      ...bankPersonEntity,
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
          ...bankPersonEntity,
          createdDate: convertDateTimeFromServer(bankPersonEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(bankPersonEntity.lastModifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="letsBankApp.bankPerson.home.createOrEditLabel"
            data-cy="BankPersonCreateUpdateHeading"
          >
            <Translate contentKey="letsBankApp.bankPerson.home.createOrEditLabel">
              Create or edit a BankPerson
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
                  id="bank-person-id"
                  label={translate('letsBankApp.bankPerson.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('letsBankApp.bankPerson.citizenId')}
                id="bank-person-citizenId"
                name="citizenId"
                data-cy="citizenId"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankPerson.thaiName')}
                id="bank-person-thaiName"
                name="thaiName"
                data-cy="thaiName"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankPerson.englishName')}
                id="bank-person-englishName"
                name="englishName"
                data-cy="englishName"
                type="text"
              />
              <ValidatedField
                label={translate('letsBankApp.bankPerson.createdBy')}
                id="bank-person-createdBy"
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
                label={translate('letsBankApp.bankPerson.createdDate')}
                id="bank-person-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('letsBankApp.bankPerson.lastModifiedBy')}
                id="bank-person-lastModifiedBy"
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
                label={translate('letsBankApp.bankPerson.lastModifiedDate')}
                id="bank-person-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/bank-person"
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

export default BankPersonUpdate;
