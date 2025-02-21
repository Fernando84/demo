import dayjs from 'dayjs';
import { IBankPerson } from 'app/shared/model/bank-person.model';

export interface IBankCustomer {
  id?: string;
  email?: string | null;
  pinCode?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  person?: IBankPerson | null;
}

export const defaultValue: Readonly<IBankCustomer> = {};
