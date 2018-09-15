import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ModeloVestuario } from './modelo-vestuario.model';
import { ModeloVestuarioPopupService } from './modelo-vestuario-popup.service';
import { ModeloVestuarioService } from './modelo-vestuario.service';
import { ConfiguracaoProdutoDialogComponent } from '../configuracao-produto/configuracao-produto-dialog.component';
import { ConfiguracaoProdutoPopupService } from '../configuracao-produto/configuracao-produto-popup.service';

@Component({
    selector: 'jhi-modelo-vestuario-dialog',
    templateUrl: './modelo-vestuario-dialog.component.html'
})
export class ModeloVestuarioDialogComponent implements OnInit {

    modeloVestuario: ModeloVestuario;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private modeloVestuarioService: ModeloVestuarioService,
        private eventManager: JhiEventManager,
        private configuracaoProdutoPopupService: ConfiguracaoProdutoPopupService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.modeloVestuario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeloVestuarioService.update(this.modeloVestuario));
        } else {
            this.subscribeToSaveResponse(
                this.modeloVestuarioService.create(this.modeloVestuario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ModeloVestuario>>) {
        result.subscribe((res: HttpResponse<ModeloVestuario>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ModeloVestuario) {
        this.eventManager.broadcast({ name: 'modeloVestuarioListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    criarConfiguracaoProduto() {
        this.configuracaoProdutoPopupService.newOrUpdate(ConfiguracaoProdutoDialogComponent as Component).then((result) => {
            result.result.then((p_result) => {
                // this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
                console.log(p_result);
            }, (p_reason) => {
                console.log(p_reason);
                // this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
                // this.ngbModalRef = null;
            });
        });
    }
}

@Component({
    selector: 'jhi-modelo-vestuario-popup',
    template: ''
})
export class ModeloVestuarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloVestuarioPopupService: ModeloVestuarioPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modeloVestuarioPopupService
                    .open(ModeloVestuarioDialogComponent as Component, params['id']);
            } else {
                this.modeloVestuarioPopupService
                    .open(ModeloVestuarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
