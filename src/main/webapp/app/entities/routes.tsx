import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UnidadAcademica from './unidad-academica';
import SubunidadAcademica from './subunidad-academica';
import ProgramaAcademico from './programa-academico';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="unidad-academica/*" element={<UnidadAcademica />} />
        <Route path="subunidad-academica/*" element={<SubunidadAcademica />} />
        <Route path="programa-academico/*" element={<ProgramaAcademico />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
