import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankTransaction from './bank-transaction';
import BankTransactionDetail from './bank-transaction-detail';
import BankTransactionUpdate from './bank-transaction-update';
import BankTransactionDeleteDialog from './bank-transaction-delete-dialog';

const BankTransactionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankTransaction />} />
    <Route path="new" element={<BankTransactionUpdate />} />
    <Route path=":id">
      <Route index element={<BankTransactionDetail />} />
      <Route path="edit" element={<BankTransactionUpdate />} />
      <Route path="delete" element={<BankTransactionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankTransactionRoutes;
