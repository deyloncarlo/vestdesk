/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { EtapaProducaoDetailComponent } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao-detail.component';
import { EtapaProducaoService } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao.service';
import { EtapaProducao } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao.model';

describe('Component Tests', () => {

    describe('EtapaProducao Management Detail Component', () => {
        let comp: EtapaProducaoDetailComponent;
        let fixture: ComponentFixture<EtapaProducaoDetailComponent>;
        let service: EtapaProducaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [EtapaProducaoDetailComponent],
                providers: [
                    EtapaProducaoService
                ]
            })
            .overrideTemplate(EtapaProducaoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtapaProducaoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapaProducaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EtapaProducao(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.etapaProducao).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
