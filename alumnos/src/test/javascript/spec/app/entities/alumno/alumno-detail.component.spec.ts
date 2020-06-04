import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlumnosTestModule } from '../../../test.module';
import { AlumnoDetailComponent } from 'app/entities/alumno/alumno-detail.component';
import { Alumno } from 'app/shared/model/alumno.model';

describe('Component Tests', () => {
  describe('Alumno Management Detail Component', () => {
    let comp: AlumnoDetailComponent;
    let fixture: ComponentFixture<AlumnoDetailComponent>;
    const route = ({ data: of({ alumno: new Alumno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlumnosTestModule],
        declarations: [AlumnoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlumnoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlumnoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alumno on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alumno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
