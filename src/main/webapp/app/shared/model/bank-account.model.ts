import dayjs from 'dayjs';
import { IBankPerson } from 'app/shared/model/bank-person.model';

export interface IBankAccount {
  id?: string;
  accountNumber?: string | null;
  balance?: number | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  person?: IBankPerson | null;
}

export const defaultValue: Readonly<IBankAccount> = {};
