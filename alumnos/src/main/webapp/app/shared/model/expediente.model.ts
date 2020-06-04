import { IAlumno } from 'app/shared/model/alumno.model';

export interface IExpediente {
  id?: number;
  numero?: string;
  alumno?: IAlumno;
}

export class Expediente implements IExpediente {
  constructor(public id?: number, public numero?: string, public alumno?: IAlumno) {}
}
