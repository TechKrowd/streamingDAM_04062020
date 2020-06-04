import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAlumno } from 'app/shared/model/alumno.model';

type EntityResponseType = HttpResponse<IAlumno>;
type EntityArrayResponseType = HttpResponse<IAlumno[]>;

@Injectable({ providedIn: 'root' })
export class AlumnoService {
  public resourceUrl = SERVER_API_URL + 'api/alumnos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/alumnos';

  constructor(protected http: HttpClient) {}

  create(alumno: IAlumno): Observable<EntityResponseType> {
    return this.http.post<IAlumno>(this.resourceUrl, alumno, { observe: 'response' });
  }

  update(alumno: IAlumno): Observable<EntityResponseType> {
    return this.http.put<IAlumno>(this.resourceUrl, alumno, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlumno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlumno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlumno[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
