/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloVestuarioDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario-delete-dialog.component';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario/modelo-vestuario.service';

describe('Component Tests', () => {

    describe('ModeloVestuario Management Delete Component', () => {
        let comp: ModeloVestuarioDeleteDialogComponent;
        let fixture: ComponentFixture<ModeloVestuarioDeleteDialogComponent>;
        let service: ModeloVestuarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloVestuarioDeleteDialogComponent],
                providers: [
                    ModeloVestuarioService
                ]
            })
            .overrideTemplate(ModeloVestuarioDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloVestuarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloVestuarioService);
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
