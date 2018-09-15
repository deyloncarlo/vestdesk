/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoProdutoDialogComponent } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto-dialog.component';
import { ConfiguracaoProdutoService } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.service';
import { ConfiguracaoProduto } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.model';
import { ModeloVestuarioService } from '../../../../../../main/webapp/app/entities/modelo-vestuario';

describe('Component Tests', () => {

    describe('ConfiguracaoProduto Management Dialog Component', () => {
        let comp: ConfiguracaoProdutoDialogComponent;
        let fixture: ComponentFixture<ConfiguracaoProdutoDialogComponent>;
        let service: ConfiguracaoProdutoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoProdutoDialogComponent],
                providers: [
                    ModeloVestuarioService,
                    ConfiguracaoProdutoService
                ]
            })
            .overrideTemplate(ConfiguracaoProdutoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoProdutoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoProdutoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ConfiguracaoProduto(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.configuracaoProduto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'configuracaoProdutoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ConfiguracaoProduto();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.configuracaoProduto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'configuracaoProdutoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
