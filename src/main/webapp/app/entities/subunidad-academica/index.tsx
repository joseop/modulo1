import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SubunidadAcademica from './subunidad-academica';
import SubunidadAcademicaDetail from './subunidad-academica-detail';
import SubunidadAcademicaUpdate from './subunidad-academica-update';
import SubunidadAcademicaDeleteDialog from './subunidad-academica-delete-dialog';

const SubunidadAcademicaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SubunidadAcademica />} />
    <Route path="new" element={<SubunidadAcademicaUpdate />} />
    <Route path=":id">
      <Route index element={<SubunidadAcademicaDetail />} />
      <Route path="edit" element={<SubunidadAcademicaUpdate />} />
      <Route path="delete" element={<SubunidadAcademicaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubunidadAcademicaRoutes;
