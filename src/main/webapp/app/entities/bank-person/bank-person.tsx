import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import {
  JhiItemCount,
  JhiPagination,
  TextFormat,
  Translate,
  getPaginationState,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './bank-person.reducer';

export const BankPerson = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(
      getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'),
      pageLocation.search,
    ),
  );

  const bankPersonList = useAppSelector(state => state.bankPerson.entities);
  const loading = useAppSelector(state => state.bankPerson.loading);
  const totalItems = useAppSelector(state => state.bankPerson.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="bank-person-heading" data-cy="BankPersonHeading">
        <Translate contentKey="letsBankApp.bankPerson.home.title">Bank People</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="letsBankApp.bankPerson.home.refreshListLabel">
              Refresh List
            </Translate>
          </Button>
          <Link
            to="/bank-person/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="letsBankApp.bankPerson.home.createLabel">
              Create new Bank Person
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankPersonList && bankPersonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="letsBankApp.bankPerson.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('citizenId')}>
                  <Translate contentKey="letsBankApp.bankPerson.citizenId">Citizen Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('citizenId')} />
                </th>
                <th className="hand" onClick={sort('thaiName')}>
                  <Translate contentKey="letsBankApp.bankPerson.thaiName">Thai Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('thaiName')} />
                </th>
                <th className="hand" onClick={sort('englishName')}>
                  <Translate contentKey="letsBankApp.bankPerson.englishName">
                    English Name
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('englishName')} />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="letsBankApp.bankPerson.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="letsBankApp.bankPerson.createdDate">
                    Created Date
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('lastModifiedBy')}>
                  <Translate contentKey="letsBankApp.bankPerson.lastModifiedBy">
                    Last Modified By
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastModifiedBy')} />
                </th>
                <th className="hand" onClick={sort('lastModifiedDate')}>
                  <Translate contentKey="letsBankApp.bankPerson.lastModifiedDate">
                    Last Modified Date
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastModifiedDate')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankPersonList.map((bankPerson, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bank-person/${bankPerson.id}`} color="link" size="sm">
                      {bankPerson.id}
                    </Button>
                  </td>
                  <td>{bankPerson.citizenId}</td>
                  <td>{bankPerson.thaiName}</td>
                  <td>{bankPerson.englishName}</td>
                  <td>{bankPerson.createdBy}</td>
                  <td>
                    {bankPerson.createdDate ? (
                      <TextFormat
                        type="date"
                        value={bankPerson.createdDate}
                        format={APP_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{bankPerson.lastModifiedBy}</td>
                  <td>
                    {bankPerson.lastModifiedDate ? (
                      <TextFormat
                        type="date"
                        value={bankPerson.lastModifiedDate}
                        format={APP_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/bank-person/${bankPerson.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/bank-person/${bankPerson.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/bank-person/${bankPerson.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="letsBankApp.bankPerson.home.notFound">
                No Bank People found
              </Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={bankPersonList && bankPersonList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount
              page={paginationState.activePage}
              total={totalItems}
              itemsPerPage={paginationState.itemsPerPage}
              i18nEnabled
            />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default BankPerson;
