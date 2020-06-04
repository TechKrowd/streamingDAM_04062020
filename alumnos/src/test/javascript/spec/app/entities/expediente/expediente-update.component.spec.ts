import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AlumnosTestModule } from '../../../test.module';
import { ExpedienteUpdateComponent } from 'app/entities/expediente/expediente-update.component';
import { ExpedienteService } from 'app/entities/expediente/expediente.service';
import { Expediente } from 'app/shared/model/expediente.model';

describe('Component Tests', () => {
  describe('Expediente Management Update Component', () => {
    let comp: ExpedienteUpdateComponent;
    let fixture: ComponentFixture<ExpedienteUpdateComponent>;
    let service: ExpedienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlumnosTestModule],
        declarations: [ExpedienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExpedienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpedienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpedienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Expediente(123);
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
        const entity = new Expediente();
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
