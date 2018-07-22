/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloDetailComponent } from '../../../../../../main/webapp/app/entities/modelo/modelo-detail.component';
import { ModeloService } from '../../../../../../main/webapp/app/entities/modelo/modelo.service';
import { Modelo } from '../../../../../../main/webapp/app/entities/modelo/modelo.model';

describe('Component Tests', () => {

    describe('Modelo Management Detail Component', () => {
        let comp: ModeloDetailComponent;
        let fixture: ComponentFixture<ModeloDetailComponent>;
        let service: ModeloService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloDetailComponent],
                providers: [
                    ModeloService
                ]
            })
            .overrideTemplate(ModeloDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Modelo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.modelo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
