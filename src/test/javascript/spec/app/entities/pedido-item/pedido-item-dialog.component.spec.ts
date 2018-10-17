/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { PedidoItemDialogComponent } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item-dialog.component';
import { PedidoItemService } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.service';
import { PedidoItem } from '../../../../../../main/webapp/app/entities/pedido-item/pedido-item.model';

describe('Component Tests', () => {

    describe('PedidoItem Management Dialog Component', () => {
        let comp: PedidoItemDialogComponent;
        let fixture: ComponentFixture<PedidoItemDialogComponent>;
        let service: PedidoItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [PedidoItemDialogComponent],
                providers: [
                    PedidoItemService
                ]
            })
            .overrideTemplate(PedidoItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PedidoItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pedidoItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pedidoItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PedidoItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pedidoItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pedidoItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
