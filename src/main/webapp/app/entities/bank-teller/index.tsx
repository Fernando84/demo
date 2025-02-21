import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankTeller from './bank-teller';
import BankTellerDetail from './bank-teller-detail';
import BankTellerUpdate from './bank-teller-update';
import BankTellerDeleteDialog from './bank-teller-delete-dialog';

const BankTellerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankTeller />} />
    <Route path="new" element={<BankTellerUpdate />} />
    <Route path=":id">
      <Route index element={<BankTellerDetail />} />
      <Route path="edit" element={<BankTellerUpdate />} />
      <Route path="delete" element={<BankTellerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankTellerRoutes;
