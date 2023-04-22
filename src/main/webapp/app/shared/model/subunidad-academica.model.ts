import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';
import { IUnidadAcademica } from 'app/shared/model/unidad-academica.model';

export interface ISubunidadAcademica {
  id?: number;
  codigoSubunidad?: number | null;
  nombreSubunidad?: string | null;
  tipoSubunidad?: string | null;
  nombreJefe?: string | null;
  descripcion?: string | null;
  programaAcademicos?: IProgramaAcademico[] | null;
  unidadAcademica?: IUnidadAcademica | null;
}

export const defaultValue: Readonly<ISubunidadAcademica> = {};
