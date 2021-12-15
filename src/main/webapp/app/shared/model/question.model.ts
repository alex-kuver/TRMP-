export interface IQuestion {
  id?: number;
  question?: string;
  isAdd?: boolean;
  questionTypeId?: number;
}

export const defaultValue: Readonly<IQuestion> = {
  isAdd: false,
};
