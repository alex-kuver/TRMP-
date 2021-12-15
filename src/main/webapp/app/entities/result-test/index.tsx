import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResultTest from './result-test';
import ResultTestDetail from './result-test-detail';
import ResultTestUpdate from './result-test-update';
import ResultTestDeleteDialog from './result-test-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResultTestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResultTestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResultTestDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResultTest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResultTestDeleteDialog} />
  </>
);

export default Routes;
