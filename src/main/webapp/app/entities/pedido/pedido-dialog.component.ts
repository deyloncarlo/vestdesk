import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Pedido, StatusPedido, TipoPedido } from './pedido.model';
import { PedidoPopupService } from './pedido-popup.service';
import { PedidoService } from './pedido.service';
import { ClientePopupService, ClienteInputComponent, Cliente } from '../cliente';
import { Modelo } from '../modelo-vestuario';
import { Tamanho } from '../configuracao-produto';
import { Cor, CorService } from '../cor';
import { PedidoItem, FormaPagamento } from '../pedido-item';
import { Produto, ProdutoPopupService, ProdutoService } from '../produto';
import { statSync } from 'fs';
import { ProdutoInputComponent } from '../produto/produto-input.component';
import { LayoutPopupService, Layout } from '../layout';
import { LayoutInputComponent } from '../layout/layout-input.component';
import { ConfiguracaoLayout } from '../configuracao-layout';
import { JhiAlertErrorComponent } from '../../shared';
import { ENUM } from '../../shared/enum'

@Component({
    selector: 'jhi-pedido-dialog',
    templateUrl: './pedido-dialog.component.html'
})
export class PedidoDialogComponent implements OnInit {

    listaCor: Cor[];

    pedido: Pedido;
    isSaving: boolean;
    dataCriacaoDp: any;
    statusPedido: any;
    nomeRoupa: string;
    telefone: string;
    modelo: Modelo;
    tamanho: Tamanho;
    cor: Cor;
    produto: Produto;
    quantidade: number;
    clienteCamisa: string;
    primeiroPagamento: number;
    formaPrimeiroPagamento: FormaPagamento;
    produtos: Produto[];
    valorTotalPedido: number; // Exibir o total do Preço
    valorRestantePedido: number;
    esconderCampos: any;
    tamanhoGrid: any;
    produtoNaoEncontrado: boolean;
    clienteTextoLivre: boolean;
    clienteNaoSelecionado: boolean;
    valorVenda: any[];
    listaEnum;
    valorTotalRecebido: number;

    constructor(
        public activeModal: NgbActiveModal,
        private pedidoService: PedidoService,
        private eventManager: JhiEventManager,
        private clientePopupService: ClientePopupService,
        private corService: CorService,
        private jhiAlertService: JhiAlertService,
        private ngbModal: NgbModal,
        private produtoPopupService: ProdutoPopupService,
        private layoutPopupService: LayoutPopupService,
        private produtoService: ProdutoService,
        private parseLinks: JhiParseLinks,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.valorVenda = [];
        this.listaEnum = ENUM;
        this.corService.query()
            .subscribe((res: HttpResponse<Cor[]>) => { this.listaCor = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.produto = new Produto();
        if (this.pedido.cliente == null) {
            this.clienteTextoLivre = true;
            this.pedido.cliente = new Cliente();
        } else {
            this.clienteTextoLivre = false;
        }
        if (this.pedido.layout == null) {
            this.pedido.layout = new Layout();
        }
        if (!this.pedido.listaConfiguracaoLayout) {
            this.pedido.listaConfiguracaoLayout = [];
        }
        if (!this.pedido.listaPedidoItem) {
            this.pedido.listaPedidoItem = [];
        }
        if (!this.pedido.id) {
            this.pedido.tipoPedido = TipoPedido.VENDA;
        } else {
            this.pedido.listaPedidoItem.forEach((pedidoItem) => {
                if (this.valorVenda[pedidoItem.produto.modelo] == null || this.valorVenda[pedidoItem.produto.modelo] == "") {
                    this.valorVenda[pedidoItem.produto.modelo] = pedidoItem.valor;
                }
            });
        }
        this.atualizarTotal();
        this.esconderCampos = false;
        this.tamanhoGrid = {
            height: '350px'
        };
        // this.pedido.dataPrevisao = { day: this.calendar.getToday().day, month: this.calendar.getToday().month + 1, year: this.calendar.getToday().year };
    }

    clear(content) {
        this.ngbModal.open(content).result.then((result) => {
            if (result == 'SIM') {
                this.activeModal.dismiss('cancel');
            }
        }, (reason) => {
        });
    }

    save(content) {

        if (!this.pedido.cliente.id && !this.pedido.nomeCliente) {
            this.clienteNaoSelecionado = true;
            setTimeout(() => this.clienteNaoSelecionado = false, 3000);
            return;
        }
        this.isSaving = true;
        if (this.pedido.statusPedido == null || this.pedido.statusPedido == StatusPedido.RASCUNHO) {
            this.ngbModal.open(content).result.then((result) => {
                this.pedido.statusPedido = result;
                this.salvar();
            }, (reason) => {
            });
        } else {
            this.salvar();
        }

    }

    esconder() {
        if (this.esconderCampos) {
            this.esconderCampos = false;
            this.tamanhoGrid = {
                height: '350px'
            };
        } else {
            this.esconderCampos = true;
            this.tamanhoGrid = {
                height: '500px'
            };
        }
    }

    selecionarClienteTextoLivre(textoClienteLivre) {
        this.clienteTextoLivre = textoClienteLivre;
    }

    limparListaPedidoItem() {
        if (this.pedido.listaPedidoItem) {
            this.pedido.listaPedidoItem.splice(0, this.pedido.listaPedidoItem.length);
        }
    }

    private salvar() {
        if (this.clienteTextoLivre) {
            this.pedido.cliente = null;
        }
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(
                this.pedidoService.create(this.pedido));
        }
    }

    private onError(error: any) {
        if (error.status == '404') {
            this.produtoNaoEncontrado = true;
            setTimeout(() => this.produtoNaoEncontrado = false, 3000);
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pedido>>) {
        result.subscribe((res: HttpResponse<Pedido>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => {
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

    private onSuccess(data, headers) {
        this.adcionarPedidoItem(data);
        this.limparCampos();
    }

    private limparCampos() {
        this.modelo = null;
        this.cor = null;
        this.modelo = null;
        this.tamanho = null;
        this.primeiroPagamento = null;
        this.formaPrimeiroPagamento = null;
        this.quantidade = null;
    }

    private atualizarTotal() {
        this.valorRestantePedido = 0;
        this.valorTotalPedido = 0;
        this.valorTotalRecebido = 0;
        this.pedido.listaPedidoItem.forEach(element => {
            this.valorTotalRecebido += element.primeiroPagamento;
            this.valorRestantePedido += ((element.valor * element.quantidade) - element.primeiroPagamento);
            this.valorTotalPedido += element.valor * element.quantidade;
        });
    }

    private adcionarPedidoItem(p_produto) {
        if (!this.pedido.listaPedidoItem) {
            this.pedido.listaPedidoItem = new Array<PedidoItem>();
        }
        const pedidoItem = this.createNewPedidoItem(p_produto);
        this.pedido.listaPedidoItem.unshift(pedidoItem);
        this.atualizarTotal();
    }

    private createNewPedidoItem(p_produto: any) {
        const pedidoItem = new PedidoItem();
        pedidoItem.quantidade = this.quantidade;
        pedidoItem.telefone = this.telefone;
        pedidoItem.clienteCamisa = this.clienteCamisa;
        pedidoItem.nomeRoupa = this.nomeRoupa;
        pedidoItem.produto = p_produto;
        pedidoItem.primeiroPagamento = this.primeiroPagamento;
        pedidoItem.formaPrimeiroPagamento = this.formaPrimeiroPagamento;
        pedidoItem.valor = this.valorVenda[pedidoItem.produto.modelo];
        return pedidoItem;
    }

    onClickInsert() {
        this.produtoService.obterProduto({
            modelo: this.modelo,
            tamanho: this.tamanho,
            corId: this.cor.id
        }).subscribe(
            (res: HttpResponse<Produto>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res)
        );
    }

    atuliazandoValorVendaModelo() {
        if (this.modelo) {
            let selecionados = this.pedido.listaPedidoItem.filter((pedidoItem) => {
                return pedidoItem.produto.modelo == this.modelo;
            });

            selecionados.forEach((pedidoItem) => {
                pedidoItem.valor = this.valorVenda[this.modelo];
            });
        }
    }

    criarProduto() {
        const produto = new Produto();
        produto.tamanho = this.tamanho;
        produto.modelo = this.modelo;
        produto.cor = this.cor;
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
        this.layoutPopupService.openLista(LayoutInputComponent as Component, this.pedido.listaConfiguracaoLayout)
            .then((resolve) => {
                resolve.result.then((listaConfiguracaoLayout) => {
                    if (listaConfiguracaoLayout != null) {
                        this.pedido.listaConfiguracaoLayout = listaConfiguracaoLayout;
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
