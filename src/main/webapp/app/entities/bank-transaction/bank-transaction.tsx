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

import { getEntities } from './bank-transaction.reducer';

export const BankTransaction = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(
      getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'),
      pageLocation.search,
    ),
  );

  const bankTransactionList = useAppSelector(state => state.bankTransaction.entities);
  const loading = useAppSelector(state => state.bankTransaction.loading);
  const totalItems = useAppSelector(state => state.bankTransaction.totalItems);

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
      <h2 id="bank-transaction-heading" data-cy="BankTransactionHeading">
        <Translate contentKey="letsBankApp.bankTransaction.home.title">Bank Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="letsBankApp.bankTransaction.home.refreshListLabel">
              Refresh List
            </Translate>
          </Button>
          <Link
            to="/bank-transaction/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="letsBankApp.bankTransaction.home.createLabel">
              Create new Bank Transaction
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankTransactionList && bankTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="letsBankApp.bankTransaction.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="letsBankApp.bankTransaction.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('channel')}>
                  <Translate contentKey="letsBankApp.bankTransaction.channel">Channel</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('channel')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="letsBankApp.bankTransaction.amount">Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th className="hand" onClick={sort('balance')}>
                  <Translate contentKey="letsBankApp.bankTransaction.balance">Balance</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('balance')} />
                </th>
                <th className="hand" onClick={sort('targetAccountId')}>
                  <Translate contentKey="letsBankApp.bankTransaction.targetAccountId">
                    Target Account Id
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetAccountId')} />
                </th>
                <th className="hand" onClick={sort('transactionDate')}>
                  <Translate contentKey="letsBankApp.bankTransaction.transactionDate">
                    Transaction Date
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('transactionDate')} />
                </th>
                <th className="hand" onClick={sort('remark')}>
                  <Translate contentKey="letsBankApp.bankTransaction.remark">Remark</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('remark')} />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="letsBankApp.bankTransaction.createdBy">
                    Created By
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="letsBankApp.bankTransaction.createdDate">
                    Created Date
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('lastModifiedBy')}>
                  <Translate contentKey="letsBankApp.bankTransaction.lastModifiedBy">
                    Last Modified By
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastModifiedBy')} />
                </th>
                <th className="hand" onClick={sort('lastModifiedDate')}>
                  <Translate contentKey="letsBankApp.bankTransaction.lastModifiedDate">
                    Last Modified Date
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastModifiedDate')} />
                </th>
                <th>
                  <Translate contentKey="letsBankApp.bankTransaction.account">Account</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankTransactionList.map((bankTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/bank-transaction/${bankTransaction.id}`}
                      color="link"
                      size="sm"
                    >
                      {bankTransaction.id}
                    </Button>
                  </td>
                  <td>{bankTransaction.code}</td>
                  <td>{bankTransaction.channel}</td>
                  <td>{bankTransaction.amount}</td>
                  <td>{bankTransaction.balance}</td>
                  <td>{bankTransaction.targetAccountId}</td>
                  <td>
                    {bankTransaction.transactionDate ? (
                      <TextFormat
                        type="date"
                        value={bankTransaction.transactionDate}
                        format={APP_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{bankTransaction.remark}</td>
                  <td>{bankTransaction.createdBy}</td>
                  <td>
                    {bankTransaction.createdDate ? (
                      <TextFormat
                        type="date"
                        value={bankTransaction.createdDate}
                        format={APP_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{bankTransaction.lastModifiedBy}</td>
                  <td>
                    {bankTransaction.lastModifiedDate ? (
                      <TextFormat
                        type="date"
                        value={bankTransaction.lastModifiedDate}
                        format={APP_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {bankTransaction.account ? (
                      <Link to={`/bank-account/${bankTransaction.account.id}`}>
                        {bankTransaction.account.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/bank-transaction/${bankTransaction.id}`}
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
                        to={`/bank-transaction/${bankTransaction.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/bank-transaction/${bankTransaction.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="letsBankApp.bankTransaction.home.notFound">
                No Bank Transactions found
              </Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={bankTransactionList && bankTransactionList.length > 0 ? '' : 'd-none'}>
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

export default BankTransaction;
