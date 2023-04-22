import { ISubunidadAcademica } from 'app/shared/model/subunidad-academica.model';

export interface IProgramaAcademico {
  id?: number;
  codigoPrograma?: number | null;
  nombrePrograma?: string | null;
  codigoSnies?: number | null;
  nombreEncargado?: string | null;
  tipoPrograma?: string | null;
  modalidadPrograma?: string | null;
  contactosAyuda?: string | null;
  subunidadAcademica?: ISubunidadAcademica | null;
}

export const defaultValue: Readonly<IProgramaAcademico> = {};
