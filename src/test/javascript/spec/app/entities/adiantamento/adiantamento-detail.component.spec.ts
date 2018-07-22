/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { AdiantamentoDetailComponent } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento-detail.component';
import { AdiantamentoService } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.service';
import { Adiantamento } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.model';

describe('Component Tests', () => {

    describe('Adiantamento Management Detail Component', () => {
        let comp: AdiantamentoDetailComponent;
        let fixture: ComponentFixture<AdiantamentoDetailComponent>;
        let service: AdiantamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [AdiantamentoDetailComponent],
                providers: [
                    AdiantamentoService
                ]
            })
            .overrideTemplate(AdiantamentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdiantamentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdiantamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Adiantamento(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.adiantamento).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
