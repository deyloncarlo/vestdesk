import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Produto } from './produto.model';
import { ProdutoService } from './produto.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { EVENT_MANAGER_BROADCAST } from "../../app.constants";

@Component({
    selector: 'jhi-produto',
    templateUrl: './produto.component.html'
})
export class ProdutoComponent implements OnInit, OnDestroy {

    filterSettings: any;
    fieldList: any[];
    filterData: {};
    produtos: Produto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private produtoService: ProdutoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.produtos = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        this.configureFilterForm();
        setTimeout(()=>{this.loadAll()}, 1);
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProdutos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    loadAll() {
        this.produtoService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            codigo: this.filterData["codigo"],
            descricao: this.filterData["descricao"],
            modelo: this.filterData["modelo"],
            tamanho: this.filterData["tamanho"]
        }).subscribe(
            (res: HttpResponse<Produto[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filter(event) {
        this.reset();
    }

    reset() {
        this.page = 0;
        this.produtos = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    private configureFilterForm() {
        this.filterData = {};

        this.filterSettings = {
            hideFilterButton: true,
            filterOnFieldValueChange: true
        };

        this.fieldList = [
            {
                name: "codigo",
                type: "text",
                label: "vestdeskApp.produto.codigo"
            },
            {
                name: "descricao",
                type: "text",
                label: "vestdeskApp.produto.descricao"
            },
            {
                name: "modelo",
                type: "select",
                label: "vestdeskApp.produto.modelo",
                enumName: "Modelo"
            },
            {
                name: "tamanho",
                type: "select",
                label: "vestdeskApp.produto.tamanho",
                enumName: "Tamanho"
            }
        ]
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    trackId(index: number, item: Produto) {
        return item.id;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.produtos.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerChangeInProdutos() {
        this.eventSubscriber = this.eventManager.subscribe('produtoListModification', (response) => {
            if (response.content != EVENT_MANAGER_BROADCAST.content.NOT_RELOAD) {
                this.reset();
            }
        });
    }
}
