/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoComponent } from '../../../../../../main/webapp/app/entities/pedido/pedido.component';
import { PedidoService } from '../../../../../../main/webapp/app/entities/pedido/pedido.service';
import { Pedido } from '../../../../../../main/webapp/app/entities/pedido/pedido.model';

describe('Component Tests', () => {

    describe('Pedido Management Component', () => {
        let comp: PedidoComponent;
        let fixture: ComponentFixture<PedidoComponent>;
        let service: PedidoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoComponent],
                providers: [
                    PedidoService
                ]
            })
            .overrideTemplate(PedidoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Pedido(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pedidos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
