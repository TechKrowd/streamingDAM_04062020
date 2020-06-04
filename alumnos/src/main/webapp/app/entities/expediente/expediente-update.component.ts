import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IExpediente, Expediente } from 'app/shared/model/expediente.model';
import { ExpedienteService } from './expediente.service';
import { IAlumno } from 'app/shared/model/alumno.model';
import { AlumnoService } from 'app/entities/alumno/alumno.service';

@Component({
  selector: 'jhi-expediente-update',
  templateUrl: './expediente-update.component.html'
})
export class ExpedienteUpdateComponent implements OnInit {
  isSaving = false;
  alumnos: IAlumno[] = [];

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
    alumno: []
  });

  constructor(
    protected expedienteService: ExpedienteService,
    protected alumnoService: AlumnoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expediente }) => {
      this.updateForm(expediente);

      this.alumnoService
        .query({ filter: 'expediente-is-null' })
        .pipe(
          map((res: HttpResponse<IAlumno[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAlumno[]) => {
          if (!expediente.alumno || !expediente.alumno.id) {
            this.alumnos = resBody;
          } else {
            this.alumnoService
              .find(expediente.alumno.id)
              .pipe(
                map((subRes: HttpResponse<IAlumno>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAlumno[]) => (this.alumnos = concatRes));
          }
        });
    });
  }

  updateForm(expediente: IExpediente): void {
    this.editForm.patchValue({
      id: expediente.id,
      numero: expediente.numero,
      alumno: expediente.alumno
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expediente = this.createFromForm();
    if (expediente.id !== undefined) {
      this.subscribeToSaveResponse(this.expedienteService.update(expediente));
    } else {
      this.subscribeToSaveResponse(this.expedienteService.create(expediente));
    }
  }

  private createFromForm(): IExpediente {
    return {
      ...new Expediente(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      alumno: this.editForm.get(['alumno'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpediente>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAlumno): any {
    return item.id;
  }
}
