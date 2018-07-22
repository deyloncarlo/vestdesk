/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { ModeloDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/modelo/modelo-delete-dialog.component';
import { ModeloService } from '../../../../../../main/webapp/app/entities/modelo/modelo.service';

describe('Component Tests', () => {

    describe('Modelo Management Delete Component', () => {
        let comp: ModeloDeleteDialogComponent;
        let fixture: ComponentFixture<ModeloDeleteDialogComponent>;
        let service: ModeloService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ModeloDeleteDialogComponent],
                providers: [
                    ModeloService
                ]
            })
            .overrideTemplate(ModeloDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloService);
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
