import bankPerson from 'app/entities/bank-person/bank-person.reducer';
import bankCustomer from 'app/entities/bank-customer/bank-customer.reducer';
import bankTeller from 'app/entities/bank-teller/bank-teller.reducer';
import bankAccount from 'app/entities/bank-account/bank-account.reducer';
import bankTransaction from 'app/entities/bank-transaction/bank-transaction.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  bankPerson,
  bankCustomer,
  bankTeller,
  bankAccount,
  bankTransaction,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
