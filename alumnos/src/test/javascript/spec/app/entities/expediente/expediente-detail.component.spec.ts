import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlumnosTestModule } from '../../../test.module';
import { ExpedienteDetailComponent } from 'app/entities/expediente/expediente-detail.component';
import { Expediente } from 'app/shared/model/expediente.model';

describe('Component Tests', () => {
  describe('Expediente Management Detail Component', () => {
    let comp: ExpedienteDetailComponent;
    let fixture: ComponentFixture<ExpedienteDetailComponent>;
    const route = ({ data: of({ expediente: new Expediente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlumnosTestModule],
        declarations: [ExpedienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExpedienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpedienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load expediente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expediente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
