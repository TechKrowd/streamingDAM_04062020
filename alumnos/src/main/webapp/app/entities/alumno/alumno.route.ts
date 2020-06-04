import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlumno, Alumno } from 'app/shared/model/alumno.model';
import { AlumnoService } from './alumno.service';
import { AlumnoComponent } from './alumno.component';
import { AlumnoDetailComponent } from './alumno-detail.component';
import { AlumnoUpdateComponent } from './alumno-update.component';

@Injectable({ providedIn: 'root' })
export class AlumnoResolve implements Resolve<IAlumno> {
  constructor(private service: AlumnoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlumno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alumno: HttpResponse<Alumno>) => {
          if (alumno.body) {
            return of(alumno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alumno());
  }
}

export const alumnoRoute: Routes = [
  {
    path: '',
    component: AlumnoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Alumnos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlumnoDetailComponent,
    resolve: {
      alumno: AlumnoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alumnos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlumnoUpdateComponent,
    resolve: {
      alumno: AlumnoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alumnos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlumnoUpdateComponent,
    resolve: {
      alumno: AlumnoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alumnos'
    },
    canActivate: [UserRouteAccessService]
  }
];
