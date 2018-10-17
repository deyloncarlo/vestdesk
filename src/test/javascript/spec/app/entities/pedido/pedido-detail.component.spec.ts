/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoDetailComponent } from '../../../../../../main/webapp/app/entities/pedido/pedido-detail.component';
import { PedidoService } from '../../../../../../main/webapp/app/entities/pedido/pedido.service';
import { Pedido } from '../../../../../../main/webapp/app/entities/pedido/pedido.model';

describe('Component Tests', () => {

    describe('Pedido Management Detail Component', () => {
        let comp: PedidoDetailComponent;
        let fixture: ComponentFixture<PedidoDetailComponent>;
        let service: PedidoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoDetailComponent],
                providers: [
                    PedidoService
                ]
            })
            .overrideTemplate(PedidoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Pedido(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pedido).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
