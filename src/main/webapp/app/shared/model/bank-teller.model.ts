import dayjs from 'dayjs';

export interface IBankTeller {
  id?: string;
  employeeId?: string | null;
  thaiName?: string | null;
  englishName?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IBankTeller> = {};
