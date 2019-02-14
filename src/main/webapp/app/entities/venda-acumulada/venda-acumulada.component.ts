import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaService } from './venda-acumulada.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Observable } from 'rxjs/Observable';
import { ProdutoService, Produto } from '../produto';
import { ENUM } from "./../../shared/enum";

@Component({
    selector: 'jhi-venda-acumulada',
    templateUrl: './venda-acumulada.component.html'
})
export class VendaAcumuladaComponent implements OnInit, OnDestroy {

    vendaAcumuladas: VendaAcumulada[];
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

    constructor(
        private vendaAcumuladaService: VendaAcumuladaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private ngbModal: NgbModal,
        private produtoService: ProdutoService
    ) {
        this.listaPorModelo = [];
        this.vendaAcumuladas = [];
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
        this.vendaAcumuladaService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<VendaAcumulada[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.vendaAcumuladas = [];
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
        this.registerChangeInVendaAcumuladas();
    }

    obterVendaAcumuladaPorModeloCor() {
        this.vendaAcumuladas.forEach((vendaAcumulada) => {
            let modeloEncontrado = this.listaPorModelo.filter((modelo) => {
                return modelo.nomeModelo == vendaAcumulada.produto.modelo;
            });
            if (modeloEncontrado.length == 0) {
                let novo = {
                    nomeModelo: vendaAcumulada.produto.modelo,
                    array: [],
                    listaCor: [],
                    listaTamanho: []
                };
                modeloEncontrado[0] = novo;
                this.listaPorModelo.push(modeloEncontrado[0]);
                // this.listaPorModelo[vendaAcumulada.produto.modelo] = [];
                // this.listaPorModelo[vendaAcumulada.produto.modelo].nomeModelo = vendaAcumulada.produto.modelo;
                // this.listaPorModelo[vendaAcumulada.produto.modelo].array = [];
                // this.listaPorModelo[vendaAcumulada.produto.modelo].listaCor = [];
                // this.listaPorModelo[vendaAcumulada.produto.modelo].listaTamanho = [];
            }
            let corId = vendaAcumulada.produto.cor.id;
            let tamanho = vendaAcumulada.produto.tamanho;

            modeloEncontrado[0].array.push(vendaAcumulada);
            if (modeloEncontrado[0].listaCor.indexOf(corId) == -1) {
                modeloEncontrado[0].listaCor.push(corId);
            }
            if (modeloEncontrado[0].listaTamanho.indexOf(tamanho) == -1) {
                modeloEncontrado[0].listaTamanho.push(tamanho);
            }

            // this.listaPorModelo[vendaAcumulada.produto.modelo].array.push(vendaAcumulada);
            // if(this.listaPorModelo[vendaAcumulada.produto.modelo].listaCor.indexOf(corId) == -1) {
            //     this.listaPorModelo[vendaAcumulada.produto.modelo].listaCor.push(corId);
            // }
            // if(this.listaPorModelo[vendaAcumulada.produto.modelo].listaTamanho.indexOf(tamanho) == -1) {
            //     this.listaPorModelo[vendaAcumulada.produto.modelo].listaTamanho.push(tamanho);
            // }
        });
        debugger
    }

    produzir(content, vendaAcumulada) {
        this.ngbModal.open(content).result.then((result) => {
            if (result == "SIM") {
                this.subscribeToSaveResponse(this.vendaAcumuladaService.produzir(vendaAcumulada));
            }
        }, (reason) => {
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VendaAcumulada>>) {
        result.subscribe((res: HttpResponse<VendaAcumulada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveError() {
        debugger
    }

    private onSaveSuccess(result: VendaAcumulada) {
        this.eventManager.broadcast({ name: 'vendaAcumuladaListModification', content: 'OK' });
        debugger
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VendaAcumulada) {
        return item.id;
    }
    registerChangeInVendaAcumuladas() {
        this.eventSubscriber = this.eventManager.subscribe('vendaAcumuladaListModification', (response) => this.reset());
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
            this.vendaAcumuladas.push(data[i]);
        }

        this.obterVendaAcumuladaPorModeloCor();
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
