import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Produto } from './produto.model';
import { ProdutoPopupService } from './produto-popup.service';
import { ProdutoService } from './produto.service';
import { ConfiguracaoProduto, ConfiguracaoProdutoService } from '../configuracao-produto';
import { Cor, CorService } from '../cor';
import { ModeloVestuarioService, ModeloVestuario } from '../modelo-vestuario';

@Component({
    selector: 'jhi-produto-dialog',
    templateUrl: './produto-dialog.component.html'
})
export class ProdutoDialogComponent implements OnInit {

    produto: Produto;
    isSaving: boolean;

    configuracaoprodutos: ConfiguracaoProduto[];

    cors: Cor[];
    listaModeloVestuario: ModeloVestuario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private produtoService: ProdutoService,
        private configuracaoProdutoService: ConfiguracaoProdutoService,
        private corService: CorService,
        private eventManager: JhiEventManager,
        private modeloVestuario: ModeloVestuarioService
    ) {
    }

    ngOnInit() {
        if (this.produto.modeloVestuario == null) {
            this.produto.modeloVestuario = new ModeloVestuario();
        }
        this.isSaving = false;
        this.modeloVestuario.query()
            .subscribe((res: HttpResponse<ModeloVestuario[]>) => { this.listaModeloVestuario = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.configuracaoProdutoService
            .query({filter: 'produto-is-null'})
            .subscribe((res: HttpResponse<ConfiguracaoProduto[]>) => {
                if (!this.produto.configuracaoProdutoId) {
                    this.configuracaoprodutos = res.body;
                } else {
                    this.configuracaoProdutoService
                        .find(this.produto.configuracaoProdutoId)
                        .subscribe((subRes: HttpResponse<ConfiguracaoProduto>) => {
                            this.configuracaoprodutos = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.corService.query()
            .subscribe((res: HttpResponse<Cor[]>) => { this.cors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    changedModeloVestuario(modeloVestuario) {
        if (modeloVestuario) {
            this.produto.configuracaoProdutoId = null;
            this.configuracaoProdutoService
            .query({filter: 'produto-is-null', idModeloVestuario: modeloVestuario.id})
            .subscribe((res: HttpResponse<ConfiguracaoProduto[]>) => {
                if (!this.produto.configuracaoProdutoId) {
                    this.configuracaoprodutos = res.body;
                } else {
                    this.configuracaoProdutoService
                        .find(this.produto.configuracaoProdutoId)
                        .subscribe((subRes: HttpResponse<ConfiguracaoProduto>) => {
                            this.configuracaoprodutos = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
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

    trackConfiguracaoProdutoById(index: number, item: ConfiguracaoProduto) {
        return item.id;
    }

    trackCorById(index: number, item: Cor) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
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
