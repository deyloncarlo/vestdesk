/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoItemDetailComponent } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item-detail.component';
import { PedidoItemService } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.service';
import { PedidoItem } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.model';

describe('Component Tests', () => {

    describe('PedidoItem Management Detail Component', () => {
        let comp: PedidoItemDetailComponent;
        let fixture: ComponentFixture<PedidoItemDetailComponent>;
        let service: PedidoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoItemDetailComponent],
                providers: [
                    PedidoItemService
                ]
            })
            .overrideTemplate(PedidoItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PedidoItem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pedidoItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
