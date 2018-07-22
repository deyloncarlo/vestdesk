import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Produto } from './produto.model';
import { ProdutoPopupService } from './produto-popup.service';
import { ProdutoService } from './produto.service';
import { ModeloVestuario, ModeloVestuarioService } from '../modelo-vestuario';

@Component({
    selector: 'jhi-produto-dialog',
    templateUrl: './produto-dialog.component.html'
})
export class ProdutoDialogComponent implements OnInit {

    produto: Produto;
    isSaving: boolean;

    modelovestuarios: ModeloVestuario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private produtoService: ProdutoService,
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
        if (this.produto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.produtoService.update(this.produto));
        } else {
            this.subscribeToSaveResponse(
                this.produtoService.create(this.produto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Produto>>) {
        result.subscribe((res: HttpResponse<Produto>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Produto) {
        this.eventManager.broadcast({ name: 'produtoListModification', content: 'OK'});
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
    selector: 'jhi-produto-popup',
    template: ''
})
export class ProdutoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private produtoPopupService: ProdutoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.produtoPopupService
                    .open(ProdutoDialogComponent as Component, params['id']);
            } else {
                this.produtoPopupService
                    .open(ProdutoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
