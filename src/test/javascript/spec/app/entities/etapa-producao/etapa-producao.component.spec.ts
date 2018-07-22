/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { EtapaProducaoComponent } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao.component';
import { EtapaProducaoService } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao.service';
import { EtapaProducao } from '../../../../../../main/webapp/app/entities/etapa-producao/etapa-producao.model';

describe('Component Tests', () => {

    describe('EtapaProducao Management Component', () => {
        let comp: EtapaProducaoComponent;
        let fixture: ComponentFixture<EtapaProducaoComponent>;
        let service: EtapaProducaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [EtapaProducaoComponent],
                providers: [
                    EtapaProducaoService
                ]
            })
            .overrideTemplate(EtapaProducaoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtapaProducaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapaProducaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EtapaProducao(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.etapaProducaos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
