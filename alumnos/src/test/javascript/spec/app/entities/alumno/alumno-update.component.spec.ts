import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AlumnosTestModule } from '../../../test.module';
import { AlumnoUpdateComponent } from 'app/entities/alumno/alumno-update.component';
import { AlumnoService } from 'app/entities/alumno/alumno.service';
import { Alumno } from 'app/shared/model/alumno.model';

describe('Component Tests', () => {
  describe('Alumno Management Update Component', () => {
    let comp: AlumnoUpdateComponent;
    let fixture: ComponentFixture<AlumnoUpdateComponent>;
    let service: AlumnoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlumnosTestModule],
        declarations: [AlumnoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlumnoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlumnoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlumnoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alumno(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alumno();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
