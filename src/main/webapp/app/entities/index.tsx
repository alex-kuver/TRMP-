import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResultTest from './result-test';
import Answer from './answer';
import Question from './question';
import QuestionType from './question-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}result-test`} component={ResultTest} />
      <ErrorBoundaryRoute path={`${match.url}answer`} component={Answer} />
      <ErrorBoundaryRoute path={`${match.url}question`} component={Question} />
      <ErrorBoundaryRoute path={`${match.url}question-type`} component={QuestionType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
