/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { VendaAcumuladaComponent } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.component';
import { VendaAcumuladaService } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.service';
import { VendaAcumulada } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.model';

describe('Component Tests', () => {

    describe('VendaAcumulada Management Component', () => {
        let comp: VendaAcumuladaComponent;
        let fixture: ComponentFixture<VendaAcumuladaComponent>;
        let service: VendaAcumuladaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [VendaAcumuladaComponent],
                providers: [
                    VendaAcumuladaService
                ]
            })
            .overrideTemplate(VendaAcumuladaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendaAcumuladaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendaAcumuladaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VendaAcumulada(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.vendaAcumuladas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
