import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankPerson from './bank-person';
import BankPersonDetail from './bank-person-detail';
import BankPersonUpdate from './bank-person-update';
import BankPersonDeleteDialog from './bank-person-delete-dialog';

const BankPersonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankPerson />} />
    <Route path="new" element={<BankPersonUpdate />} />
    <Route path=":id">
      <Route index element={<BankPersonDetail />} />
      <Route path="edit" element={<BankPersonUpdate />} />
      <Route path="delete" element={<BankPersonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankPersonRoutes;
