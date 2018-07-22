/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { QuantidadeTamanhoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho-delete-dialog.component';
import { QuantidadeTamanhoService } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.service';

describe('Component Tests', () => {

    describe('QuantidadeTamanho Management Delete Component', () => {
        let comp: QuantidadeTamanhoDeleteDialogComponent;
        let fixture: ComponentFixture<QuantidadeTamanhoDeleteDialogComponent>;
        let service: QuantidadeTamanhoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [QuantidadeTamanhoDeleteDialogComponent],
                providers: [
                    QuantidadeTamanhoService
                ]
            })
            .overrideTemplate(QuantidadeTamanhoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuantidadeTamanhoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuantidadeTamanhoService);
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
