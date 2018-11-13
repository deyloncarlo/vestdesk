import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pedido, StatusPedido, TipoPedido } from './pedido.model';
import { PedidoPopupService } from './pedido-popup.service';
import { PedidoService } from './pedido.service';
import { ClientePopupService, ClienteInputComponent, Cliente } from '../cliente';
import { Modelo } from '../modelo-vestuario';
import { Tamanho } from '../configuracao-produto';
import { Cor, CorService } from '../cor';
import { PedidoItem } from '../pedido-item';
import { Produto, ProdutoPopupService } from '../produto';
import { statSync } from 'fs';
import { ProdutoInputComponent } from '../produto/produto-input.component';
import { LayoutPopupService } from '../layout';
import { LayoutInputComponent } from '../layout/layout-input.component';

@Component({
    selector: 'jhi-pedido-dialog',
    templateUrl: './pedido-dialog.component.html'
})
export class PedidoDialogComponent implements OnInit {

    pedido: Pedido;
    isSaving: boolean;
    dataCriacaoDp: any;
    statusPedido: any;

    nomeRoupa: string;
    telefone: string;
    modelo: Modelo;
    tamanho: Tamanho;
    listaCor: Cor[];
    cor: Cor;
    produto: Produto;
    quantidade: number;

    constructor(
        public activeModal: NgbActiveModal,
        private pedidoService: PedidoService,
        private eventManager: JhiEventManager,
        private clientePopupService: ClientePopupService,
        private corService: CorService,
        private jhiAlertService: JhiAlertService,
        private ngbModal: NgbModal,
        private produtoPopupService: ProdutoPopupService,
        private layoutPopupService: LayoutPopupService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.corService.query()
            .subscribe((res: HttpResponse<Cor[]>) => { this.listaCor = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.produto = new Produto();
        if (this.pedido.cliente == null) {
            this.pedido.cliente = new Cliente();
        }
        if (this.pedido.layout == null) {
            this.pedido.layout = new Cliente();
        }
        // this.pedido.dataPrevisao = { day: this.calendar.getToday().day, month: this.calendar.getToday().month + 1, year: this.calendar.getToday().year };

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save(content) {

        this.isSaving = true;
        if (this.pedido.statusPedido == null || this.pedido.statusPedido == StatusPedido.RASCUNHO) {
            this.ngbModal.open(content).result.then((result) => {
                this.pedido.statusPedido = result;
                this.salvar();
            }, (reason) => {
                debugger
            });
        }
        else {
            this.salvar();
        }

    }

    limparListaPedidoItem() {
        this.pedido.listaPedidoItem.splice(0, this.pedido.listaPedidoItem.length);
    }

    private salvar() {
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(
                this.pedidoService.create(this.pedido));
        }
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.error.detail, null, null);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pedido>>) {
        result.subscribe((res: HttpResponse<Pedido>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => {
                debugger
                this.onSaveError();

            });
    }

    private onSaveSuccess(result: Pedido) {
        this.eventManager.broadcast({ name: 'pedidoListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    inserir() {
        if (!this.pedido.listaPedidoItem) {
            this.pedido.listaPedidoItem = new Array<PedidoItem>();
        }
        const pedidoItem = new PedidoItem();
        debugger
        if (this.pedido.tipoPedido == TipoPedido.PRODUCAO) {
            if (this.pedido.cliente != null && this.pedido.cliente.telefone) {
                pedidoItem.telefone = this.pedido.cliente.telefone;
            }
            pedidoItem.produto = this.produto;
            pedidoItem.quantidade = this.quantidade;

        } else {
            pedidoItem.telefone = this.telefone;
            pedidoItem.nomeRoupa = this.nomeRoupa;
            pedidoItem.produto = this.criarProduto();
        }
        this.pedido.listaPedidoItem.push(pedidoItem);
    }

    criarProduto() {
        const produto = new Produto();
        produto.tamanho = this.tamanho;
        produto.modelo = this.modelo;
        produto.listaCor = new Array<Produto>();
        produto.listaCor.push(this.cor);
        produto.listaCor[0].nome
        return produto;
    }

    selecionarCliente() {
        this.clientePopupService.open(ClienteInputComponent as Component)
            .then((resolve) => {
                resolve.result.then((cliente) => {
                    if (cliente != null) {
                        this.pedido.cliente = cliente;
                    }
                });
            });
    }

    selecionarLayout() {
        this.layoutPopupService.open(LayoutInputComponent as Component)
            .then((resolve) => {
                resolve.result.then((layout) => {
                    if (layout != null) {
                        this.pedido.layout = layout;
                    }
                });
            });
    }

    selecionarProduto() {
        this.produtoPopupService.open(ProdutoInputComponent as Component)
            .then((resolve) => {
                resolve.result.then((produto) => {
                    if (produto != null) {
                        this.produto = produto;
                    }
                });
            });
    }

    removerPedidoItem(indice) {
        this.pedido.listaPedidoItem.splice(indice, 1);
    }
}

@Component({
    selector: 'jhi-pedido-popup',
    template: ''
})
export class PedidoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pedidoPopupService: PedidoPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.pedidoPopupService
                    .open(PedidoDialogComponent as Component, params['id']);
            } else {
                this.pedidoPopupService
                    .open(PedidoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
