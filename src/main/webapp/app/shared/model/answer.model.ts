export interface IAnswer {
  id?: number;
  answer?: boolean;
  questionId?: number;
  resultTestId?: number;
}

export const defaultValue: Readonly<IAnswer> = {
  answer: false,
};
