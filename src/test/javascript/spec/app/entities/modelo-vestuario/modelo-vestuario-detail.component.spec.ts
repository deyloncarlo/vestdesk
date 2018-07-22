/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloVestuarioDetailComponent } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario-detail.component';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.service';
import { ModeloVestuario } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.model';

describe('Component Tests', () => {

    describe('ModeloVestuario Management Detail Component', () => {
        let comp: ModeloVestuarioDetailComponent;
        let fixture: ComponentFixture<ModeloVestuarioDetailComponent>;
        let service: ModeloVestuarioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloVestuarioDetailComponent],
                providers: [
                    ModeloVestuarioService
                ]
            })
            .overrideTemplate(ModeloVestuarioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloVestuarioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloVestuarioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ModeloVestuario(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.modeloVestuario).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
