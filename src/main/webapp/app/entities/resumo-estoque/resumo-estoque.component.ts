import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VendaAcumulada } from './../venda-acumulada';
import { VendaAcumuladaService } from './../venda-acumulada';
import { Produto, ProdutoService } from './../produto';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Observable } from 'rxjs/Observable';
import { CorService, Cor } from '../cor';
import { ENUM } from "./../../shared/enum";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-resumo-estoque',
    templateUrl: './resumo-estoque.component.html'
})
export class ResumoEstoqueComponent implements OnInit, OnDestroy {

    listaProduto: Produto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    listaPorModelo: any[];
    listaTamanho: any[];
    listaCor: Cor[];

    constructor(
        private vendaAcumuladaService: VendaAcumuladaService,
        private produtoService: ProdutoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private ngbModal: NgbModal,
        private corService: CorService,
        private route: Router,
    ) {
        this.listaPorModelo = [];
        this.listaCor = [];
        this.listaProduto = [];
        this.listaTamanho = ENUM['Tamanho'];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.listaPorModelo = [];
        this.listaCor = [];
        this.listaProduto = [];
        this.corService.query({}).subscribe(
            (res: HttpResponse<Cor[]>) => {
                for (let i = 0; i < res.body.length; i++) {
                    this.listaCor.push(res.body[i]);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.produtoService.query({
            page: this.page,
            size: 1000,
            sort: this.sort(),
        }).subscribe(
            (res: HttpResponse<Produto[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.listaProduto = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInResumoEstoque();
    }

    obterProdutoPorModeloCor() {
        this.listaProduto.forEach((produto) => {
            let modeloEncontrado = this.listaPorModelo.filter((modelo) => {
                return modelo.nomeModelo == produto.modelo;
            });
            if (modeloEncontrado.length == 0) {
                let novo = {
                    nomeModelo: produto.modelo,
                    array: []
                };
                modeloEncontrado[0] = novo;
                this.listaPorModelo.push(modeloEncontrado[0]);
            }
            modeloEncontrado[0].array.push({
                produto: produto,
                quantidadeEstoque: produto.quantidadeEstoque
            });
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VendaAcumulada) {
        return item.id;
    }
    registerChangeInResumoEstoque() {
        this.eventSubscriber = this.eventManager.subscribe('resumoEstoqueListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.listaProduto.push(data[i]);
        }

        this.obterProdutoPorModeloCor();
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
