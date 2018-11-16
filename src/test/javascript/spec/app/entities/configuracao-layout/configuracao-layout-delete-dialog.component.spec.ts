/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoLayoutDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout-delete-dialog.component';
import { ConfiguracaoLayoutService } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.service';

describe('Component Tests', () => {

    describe('ConfiguracaoLayout Management Delete Component', () => {
        let comp: ConfiguracaoLayoutDeleteDialogComponent;
        let fixture: ComponentFixture<ConfiguracaoLayoutDeleteDialogComponent>;
        let service: ConfiguracaoLayoutService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoLayoutDeleteDialogComponent],
                providers: [
                    ConfiguracaoLayoutService
                ]
            })
            .overrideTemplate(ConfiguracaoLayoutDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoLayoutDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoLayoutService);
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
