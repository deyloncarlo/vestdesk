/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { AdiantamentoDialogComponent } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento-dialog.component';
import { AdiantamentoService } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.service';
import { Adiantamento } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.model';
import { FormaPagamentoService } from '../../../../../../main/webapp/app/entities/forma-pagamento';

describe('Component Tests', () => {

    describe('Adiantamento Management Dialog Component', () => {
        let comp: AdiantamentoDialogComponent;
        let fixture: ComponentFixture<AdiantamentoDialogComponent>;
        let service: AdiantamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [AdiantamentoDialogComponent],
                providers: [
                    FormaPagamentoService,
                    AdiantamentoService
                ]
            })
            .overrideTemplate(AdiantamentoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdiantamentoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdiantamentoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Adiantamento(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.adiantamento = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adiantamentoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Adiantamento();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.adiantamento = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adiantamentoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
