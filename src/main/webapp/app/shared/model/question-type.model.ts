import { Psychotype } from 'app/shared/model/enumerations/psychotype.model';

export interface IQuestionType {
  id?: number;
  type?: Psychotype;
  coefficient?: number;
  description?: string;
}

export const defaultValue: Readonly<IQuestionType> = {};
