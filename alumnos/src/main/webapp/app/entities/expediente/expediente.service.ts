import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IExpediente } from 'app/shared/model/expediente.model';

type EntityResponseType = HttpResponse<IExpediente>;
type EntityArrayResponseType = HttpResponse<IExpediente[]>;

@Injectable({ providedIn: 'root' })
export class ExpedienteService {
  public resourceUrl = SERVER_API_URL + 'api/expedientes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/expedientes';

  constructor(protected http: HttpClient) {}

  create(expediente: IExpediente): Observable<EntityResponseType> {
    return this.http.post<IExpediente>(this.resourceUrl, expediente, { observe: 'response' });
  }

  update(expediente: IExpediente): Observable<EntityResponseType> {
    return this.http.put<IExpediente>(this.resourceUrl, expediente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExpediente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExpediente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExpediente[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
