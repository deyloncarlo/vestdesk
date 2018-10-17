/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoItemComponent } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.component';
import { PedidoItemService } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.service';
import { PedidoItem } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.model';

describe('Component Tests', () => {

    describe('PedidoItem Management Component', () => {
        let comp: PedidoItemComponent;
        let fixture: ComponentFixture<PedidoItemComponent>;
        let service: PedidoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoItemComponent],
                providers: [
                    PedidoItemService
                ]
            })
            .overrideTemplate(PedidoItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PedidoItem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pedidoItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
