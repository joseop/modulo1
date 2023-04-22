import { ISubunidadAcademica } from 'app/shared/model/subunidad-academica.model';

export interface IUnidadAcademica {
  id?: number;
  codigoUnidad?: number | null;
  nombreUnidad?: string | null;
  tipoUnidad?: string | null;
  nombreDecano?: string | null;
  descripcion?: string | null;
  subunidadAcademicas?: ISubunidadAcademica[] | null;
}

export const defaultValue: Readonly<IUnidadAcademica> = {};
