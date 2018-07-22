/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloComponent } from '../../../../../../main/webapp/app/entities/modelo/modelo.component';
import { ModeloService } from '../../../../../../main/webapp/app/entities/modelo/modelo.service';
import { Modelo } from '../../../../../../main/webapp/app/entities/modelo/modelo.model';

describe('Component Tests', () => {

    describe('Modelo Management Component', () => {
        let comp: ModeloComponent;
        let fixture: ComponentFixture<ModeloComponent>;
        let service: ModeloService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloComponent],
                providers: [
                    ModeloService
                ]
            })
            .overrideTemplate(ModeloComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Modelo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.modelos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
