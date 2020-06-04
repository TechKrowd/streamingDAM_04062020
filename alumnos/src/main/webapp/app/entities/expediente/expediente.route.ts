import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpediente, Expediente } from 'app/shared/model/expediente.model';
import { ExpedienteService } from './expediente.service';
import { ExpedienteComponent } from './expediente.component';
import { ExpedienteDetailComponent } from './expediente-detail.component';
import { ExpedienteUpdateComponent } from './expediente-update.component';

@Injectable({ providedIn: 'root' })
export class ExpedienteResolve implements Resolve<IExpediente> {
  constructor(private service: ExpedienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpediente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expediente: HttpResponse<Expediente>) => {
          if (expediente.body) {
            return of(expediente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Expediente());
  }
}

export const expedienteRoute: Routes = [
  {
    path: '',
    component: ExpedienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Expedientes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExpedienteDetailComponent,
    resolve: {
      expediente: ExpedienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expedientes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExpedienteUpdateComponent,
    resolve: {
      expediente: ExpedienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expedientes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExpedienteUpdateComponent,
    resolve: {
      expediente: ExpedienteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expedientes'
    },
    canActivate: [UserRouteAccessService]
  }
];
