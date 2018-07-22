/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { QuantidadeTamanhoDialogComponent } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho-dialog.component';
import { QuantidadeTamanhoService } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.service';
import { QuantidadeTamanho } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.model';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario';

describe('Component Tests', () => {

    describe('QuantidadeTamanho Management Dialog Component', () => {
        let comp: QuantidadeTamanhoDialogComponent;
        let fixture: ComponentFixture<QuantidadeTamanhoDialogComponent>;
        let service: QuantidadeTamanhoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [QuantidadeTamanhoDialogComponent],
                providers: [
                    ModeloVestuarioService,
                    QuantidadeTamanhoService
                ]
            })
            .overrideTemplate(QuantidadeTamanhoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuantidadeTamanhoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuantidadeTamanhoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new QuantidadeTamanho(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.quantidadeTamanho = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quantidadeTamanhoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new QuantidadeTamanho();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.quantidadeTamanho = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quantidadeTamanhoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
