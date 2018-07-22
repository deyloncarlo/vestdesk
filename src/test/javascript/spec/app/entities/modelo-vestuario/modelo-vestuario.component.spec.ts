/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloVestuarioComponent } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.component';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.service';
import { ModeloVestuario } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.model';

describe('Component Tests', () => {

    describe('ModeloVestuario Management Component', () => {
        let comp: ModeloVestuarioComponent;
        let fixture: ComponentFixture<ModeloVestuarioComponent>;
        let service: ModeloVestuarioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloVestuarioComponent],
                providers: [
                    ModeloVestuarioService
                ]
            })
            .overrideTemplate(ModeloVestuarioComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloVestuarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloVestuarioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ModeloVestuario(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.modeloVestuarios[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
