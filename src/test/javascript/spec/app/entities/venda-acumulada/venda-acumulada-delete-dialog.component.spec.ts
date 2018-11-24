/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { VendaAcumuladaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada-delete-dialog.component';
import { VendaAcumuladaService } from '../../../../../../main/webapp/app/entities/venda-acumulada/venda-acumulada.service';

describe('Component Tests', () => {

    describe('VendaAcumulada Management Delete Component', () => {
        let comp: VendaAcumuladaDeleteDialogComponent;
        let fixture: ComponentFixture<VendaAcumuladaDeleteDialogComponent>;
        let service: VendaAcumuladaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [VendaAcumuladaDeleteDialogComponent],
                providers: [
                    VendaAcumuladaService
                ]
            })
            .overrideTemplate(VendaAcumuladaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendaAcumuladaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendaAcumuladaService);
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
