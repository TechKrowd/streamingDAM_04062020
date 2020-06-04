import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlumno, Alumno } from 'app/shared/model/alumno.model';
import { AlumnoService } from './alumno.service';

@Component({
  selector: 'jhi-alumno-update',
  templateUrl: './alumno-update.component.html'
})
export class AlumnoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]]
  });

  constructor(protected alumnoService: AlumnoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alumno }) => {
      this.updateForm(alumno);
    });
  }

  updateForm(alumno: IAlumno): void {
    this.editForm.patchValue({
      id: alumno.id,
      nombre: alumno.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alumno = this.createFromForm();
    if (alumno.id !== undefined) {
      this.subscribeToSaveResponse(this.alumnoService.update(alumno));
    } else {
      this.subscribeToSaveResponse(this.alumnoService.create(alumno));
    }
  }

  private createFromForm(): IAlumno {
    return {
      ...new Alumno(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlumno>>): void {
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
}
