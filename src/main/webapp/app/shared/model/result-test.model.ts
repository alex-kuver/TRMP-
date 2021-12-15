import { Moment } from 'moment';

export interface IResultTest {
  id?: number;
  finishedAt?: string;
  demonstrativeType?: number;
  stuckType?: number;
  pedanticType?: number;
  excitableType?: number;
  hyperthymicType?: number;
  dysthymicType?: number;
  anxiouslyFearfulType?: number;
  emotionallyExaltedType?: number;
  emotiveType?: number;
  cyclothymicType?: number;
  userId?: number;
}

export const defaultValue: Readonly<IResultTest> = {};
