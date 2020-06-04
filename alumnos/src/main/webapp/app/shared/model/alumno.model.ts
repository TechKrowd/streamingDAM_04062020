import { IExpediente } from 'app/shared/model/expediente.model';

export interface IAlumno {
  id?: number;
  nombre?: string;
  expediente?: IExpediente;
}

export class Alumno implements IAlumno {
  constructor(public id?: number, public nombre?: string, public expediente?: IExpediente) {}
}
