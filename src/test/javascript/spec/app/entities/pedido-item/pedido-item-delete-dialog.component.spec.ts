/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoItemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item-delete-dialog.component';
import { PedidoItemService } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.service';

describe('Component Tests', () => {

    describe('PedidoItem Management Delete Component', () => {
        let comp: PedidoItemDeleteDialogComponent;
        let fixture: ComponentFixture<PedidoItemDeleteDialogComponent>;
        let service: PedidoItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoItemDeleteDialogComponent],
                providers: [
                    PedidoItemService
                ]
            })
            .overrideTemplate(PedidoItemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
