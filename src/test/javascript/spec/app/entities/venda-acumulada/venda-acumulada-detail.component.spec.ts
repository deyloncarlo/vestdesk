/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { VendaAcumuladaDetailComponent } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada-detail.component';
import { VendaAcumuladaService } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.service';
import { VendaAcumulada } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.model';

describe('Component Tests', () => {

    describe('VendaAcumulada Management Detail Component', () => {
        let comp: VendaAcumuladaDetailComponent;
        let fixture: ComponentFixture<VendaAcumuladaDetailComponent>;
        let service: VendaAcumuladaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [VendaAcumuladaDetailComponent],
                providers: [
                    VendaAcumuladaService
                ]
            })
            .overrideTemplate(VendaAcumuladaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendaAcumuladaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendaAcumuladaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VendaAcumulada(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vendaAcumulada).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
