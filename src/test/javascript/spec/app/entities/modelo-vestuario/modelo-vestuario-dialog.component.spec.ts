/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloVestuarioDialogComponent } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario-dialog.component';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.service';
import { ModeloVestuario } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.model';

describe('Component Tests', () => {

    describe('ModeloVestuario Management Dialog Component', () => {
        let comp: ModeloVestuarioDialogComponent;
        let fixture: ComponentFixture<ModeloVestuarioDialogComponent>;
        let service: ModeloVestuarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloVestuarioDialogComponent],
                providers: [
                    ModeloVestuarioService
                ]
            })
            .overrideTemplate(ModeloVestuarioDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloVestuarioDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloVestuarioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ModeloVestuario(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.modeloVestuario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'modeloVestuarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ModeloVestuario();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.modeloVestuario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'modeloVestuarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
