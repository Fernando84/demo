import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankPerson from './bank-person';
import BankCustomer from './bank-customer';
import BankTeller from './bank-teller';
import BankAccount from './bank-account';
import BankTransaction from './bank-transaction';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="bank-person/*" element={<BankPerson />} />
        <Route path="bank-customer/*" element={<BankCustomer />} />
        <Route path="bank-teller/*" element={<BankTeller />} />
        <Route path="bank-account/*" element={<BankAccount />} />
        <Route path="bank-transaction/*" element={<BankTransaction />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
