import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UnidadAcademica from './unidad-academica';
import UnidadAcademicaDetail from './unidad-academica-detail';
import UnidadAcademicaUpdate from './unidad-academica-update';
import UnidadAcademicaDeleteDialog from './unidad-academica-delete-dialog';

const UnidadAcademicaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UnidadAcademica />} />
    <Route path="new" element={<UnidadAcademicaUpdate />} />
    <Route path=":id">
      <Route index element={<UnidadAcademicaDetail />} />
      <Route path="edit" element={<UnidadAcademicaUpdate />} />
      <Route path="delete" element={<UnidadAcademicaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UnidadAcademicaRoutes;
