import dayjs from 'dayjs';
import { IBankAccount } from 'app/shared/model/bank-account.model';

export interface IBankTransaction {
  id?: string;
  code?: string | null;
  channel?: string | null;
  amount?: number | null;
  balance?: number | null;
  targetAccountId?: string | null;
  transactionDate?: dayjs.Dayjs | null;
  remark?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  account?: IBankAccount | null;
}

export const defaultValue: Readonly<IBankTransaction> = {};
