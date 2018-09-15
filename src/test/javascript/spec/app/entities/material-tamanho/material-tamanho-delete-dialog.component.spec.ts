/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { MaterialTamanhoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho-delete-dialog.component';
import { MaterialTamanhoService } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.service';

describe('Component Tests', () => {

    describe('MaterialTamanho Management Delete Component', () => {
        let comp: MaterialTamanhoDeleteDialogComponent;
        let fixture: ComponentFixture<MaterialTamanhoDeleteDialogComponent>;
        let service: MaterialTamanhoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [MaterialTamanhoDeleteDialogComponent],
                providers: [
                    MaterialTamanhoService
                ]
            })
            .overrideTemplate(MaterialTamanhoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialTamanhoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialTamanhoService);
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
