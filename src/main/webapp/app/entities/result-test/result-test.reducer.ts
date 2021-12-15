import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResultTest, defaultValue } from 'app/shared/model/result-test.model';

export const ACTION_TYPES = {
  FETCH_RESULTTEST_LIST: 'resultTest/FETCH_RESULTTEST_LIST',
  FETCH_RESULTTEST: 'resultTest/FETCH_RESULTTEST',
  CREATE_RESULTTEST: 'resultTest/CREATE_RESULTTEST',
  UPDATE_RESULTTEST: 'resultTest/UPDATE_RESULTTEST',
  DELETE_RESULTTEST: 'resultTest/DELETE_RESULTTEST',
  RESET: 'resultTest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResultTest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ResultTestState = Readonly<typeof initialState>;

// Reducer

export default (state: ResultTestState = initialState, action): ResultTestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESULTTEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESULTTEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESULTTEST):
    case REQUEST(ACTION_TYPES.UPDATE_RESULTTEST):
    case REQUEST(ACTION_TYPES.DELETE_RESULTTEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESULTTEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESULTTEST):
    case FAILURE(ACTION_TYPES.CREATE_RESULTTEST):
    case FAILURE(ACTION_TYPES.UPDATE_RESULTTEST):
    case FAILURE(ACTION_TYPES.DELETE_RESULTTEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESULTTEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESULTTEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESULTTEST):
    case SUCCESS(ACTION_TYPES.UPDATE_RESULTTEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESULTTEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/result-tests';

// Actions

export const getEntities: ICrudGetAllAction<IResultTest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RESULTTEST_LIST,
    payload: axios.get<IResultTest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IResultTest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESULTTEST,
    payload: axios.get<IResultTest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResultTest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESULTTEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResultTest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESULTTEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResultTest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESULTTEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
