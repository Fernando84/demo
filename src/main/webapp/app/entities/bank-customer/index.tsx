import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankCustomer from './bank-customer';
import BankCustomerDetail from './bank-customer-detail';
import BankCustomerUpdate from './bank-customer-update';
import BankCustomerDeleteDialog from './bank-customer-delete-dialog';

const BankCustomerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankCustomer />} />
    <Route path="new" element={<BankCustomerUpdate />} />
    <Route path=":id">
      <Route index element={<BankCustomerDetail />} />
      <Route path="edit" element={<BankCustomerUpdate />} />
      <Route path="delete" element={<BankCustomerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankCustomerRoutes;
