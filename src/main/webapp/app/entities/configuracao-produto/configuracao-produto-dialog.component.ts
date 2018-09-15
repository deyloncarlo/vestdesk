import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConfiguracaoProduto } from './configuracao-produto.model';
import { ConfiguracaoProdutoPopupService } from './configuracao-produto-popup.service';
import { ConfiguracaoProdutoService } from './configuracao-produto.service';
import { ModeloVestuario, ModeloVestuarioService } from '../modelo-vestuario';

@Component({
    selector: 'jhi-configuracao-produto-dialog',
    templateUrl: './configuracao-produto-dialog.component.html'
})
export class ConfiguracaoProdutoDialogComponent implements OnInit {

    configuracaoProduto: ConfiguracaoProduto;
    isSaving: boolean;

    modelovestuarios: ModeloVestuario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private configuracaoProdutoService: ConfiguracaoProdutoService,
        private modeloVestuarioService: ModeloVestuarioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.modeloVestuarioService.query()
            .subscribe((res: HttpResponse<ModeloVestuario[]>) => { this.modelovestuarios = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.configuracaoProduto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.configuracaoProdutoService.update(this.configuracaoProduto));
        } else {
            this.subscribeToSaveResponse(
                this.configuracaoProdutoService.create(this.configuracaoProduto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ConfiguracaoProduto>>) {
        result.subscribe((res: HttpResponse<ConfiguracaoProduto>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ConfiguracaoProduto) {
        this.eventManager.broadcast({ name: 'configuracaoProdutoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackModeloVestuarioById(index: number, item: ModeloVestuario) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-configuracao-produto-popup',
    template: ''
})
export class ConfiguracaoProdutoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configuracaoProdutoPopupService: ConfiguracaoProdutoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.configuracaoProdutoPopupService
                    .open(ConfiguracaoProdutoDialogComponent as Component, params['id']);
            } else {
                this.configuracaoProdutoPopupService
                    .open(ConfiguracaoProdutoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
