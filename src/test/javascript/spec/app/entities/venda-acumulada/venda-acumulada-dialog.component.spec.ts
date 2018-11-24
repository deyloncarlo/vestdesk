/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { VendaAcumuladaDialogComponent } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada-dialog.component';
import { VendaAcumuladaService } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.service';
import { VendaAcumulada } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.model';

describe('Component Tests', () => {

    describe('VendaAcumulada Management Dialog Component', () => {
        let comp: VendaAcumuladaDialogComponent;
        let fixture: ComponentFixture<VendaAcumuladaDialogComponent>;
        let service: VendaAcumuladaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [VendaAcumuladaDialogComponent],
                providers: [
                    VendaAcumuladaService
                ]
            })
            .overrideTemplate(VendaAcumuladaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendaAcumuladaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendaAcumuladaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VendaAcumulada(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.vendaAcumulada = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'vendaAcumuladaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VendaAcumulada();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.vendaAcumulada = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'vendaAcumuladaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
